package faang.school.accountservice.service;

import faang.school.accountservice.balanceValidator.BalanceValidator;
import faang.school.accountservice.dto.BalanceAuditDto;
import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.entity.BalanceAudit;
import faang.school.accountservice.exception.DataNotFoundException;
import faang.school.accountservice.mapper.BalanceAuditMapper;
import faang.school.accountservice.mapper.BalanceMapper;
import faang.school.accountservice.model.Account;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.repository.BalanceAuditRepository;
import faang.school.accountservice.repository.BalanceRepository;
import faang.school.accountservice.validator.BalanceServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final AccountRepository accountRepository;
    private final BalanceMapper balanceMapper;
    private final BalanceRepository balanceRepository;
    private final BalanceValidator balanceValidator;
    private final BalanceServiceValidator balanceServiceValidator;
    private final BalanceAuditRepository balanceAuditRepository;
    private final BalanceAuditMapper balanceAuditMapper;

    @Transactional
    public BalanceDto createBalance(Long accountId) {

        Account account = accountRepository
                .findById(accountId).orElseThrow(() -> new DataNotFoundException("Account not found"));

        balanceValidator.checkExistBalanceForAccount(account);

        Balance balance = Balance.builder()
                .account(account)
                .authorizationBalance(BigDecimal.ZERO)
                .currentBalance(BigDecimal.ZERO)
                .version(1L)
                .build();
        account.setBalance(balance);

        Balance savedBalance = balanceRepository.save(balance);
        saveBalanceAudit(balance);

        return balanceMapper.toDto(savedBalance);
    }

    @Transactional(readOnly = true)
    public BalanceDto getBalance(long accountId) {
        Balance balance = getBalanceByAccountId(accountId);
        return balanceMapper.toDto(balance);
    }

    @Transactional
    public BalanceDto increaseBalance(Balance balance, BigDecimal amount) {

        balanceValidator.isBalanceValid(balance);

        balanceValidator.isAmountValidForReplenishment(amount);
        balance.setAuthorizationBalance(balance.getAuthorizationBalance().add(amount));

        balanceRepository.save(balance);
        saveBalanceAudit(balance);
        return balanceMapper.toDto(balance);
    }

    @Transactional
    public BalanceDto reduceBalance(Balance balance, BigDecimal amount) {

        balanceValidator.isBalanceValid(balance);
    public BalanceAuditDto getBalanceAudit(long idAudit) {
        BalanceAudit balanceAudit = findEntityById(idAudit, balanceAuditRepository);
        return balanceAuditMapper.mapEntityToDto(balanceAudit);
    }

        balanceValidator.isBalanceValidForWithdrawal(balance, amount);
        balance.setAuthorizationBalance(balance.getAuthorizationBalance().subtract(amount));

        balanceRepository.save(balance);
        return balanceMapper.toDto(balance);
    private <T> T findEntityById(long id, CrudRepository<T, Long> repository) {
        Optional<T> entity = repository.findById(id);
        balanceServiceValidator.checkExistence(entity);
        return entity.get();
    }

    private Balance getBalanceByAccountId(Long accountId) {
        return balanceRepository.findByAccountId(accountId)
                .orElseThrow(() -> new DataNotFoundException("Balance not found for account id: " + accountId));
    private void saveBalanceAudit(Balance balance) {
        BalanceAudit balanceAudit = balanceAuditMapper.mapBalanceToBalanceAudit(balance);
        balanceAuditRepository.save(balanceAudit);
    }
}
