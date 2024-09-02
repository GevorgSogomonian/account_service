package faang.school.accountservice.service;

import faang.school.accountservice.dto.BalanceAuditDto;
import faang.school.accountservice.dto.BalanceDto;
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
    private final BalanceServiceValidator balanceServiceValidator;
    private final BalanceAuditRepository balanceAuditRepository;
    private final BalanceAuditMapper balanceAuditMapper;

    @Transactional
    public BalanceDto createBalance(BalanceDto balanceDto) {

        Account account = accountRepository
                .findById(balanceDto.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account not found"));

        Balance balance = Balance.builder()
                .account(account)
                .authorizationBalance(BigDecimal.ZERO)
                .currentBalance(BigDecimal.ZERO)
                .build();

        Balance savedBalance = balanceRepository.save(balance);
        saveBalanceAudit(balance);

        return balanceMapper.toDto(savedBalance);
    }

    @Transactional(readOnly = true)
    public BalanceDto getBalance(long balanceId) {
        Balance balance = balanceRepository.findById(balanceId).orElseThrow(() -> new DataNotFoundException("Balance not found"));
        return balanceMapper.toDto(balance);
    }

    @Transactional
    public BalanceDto updateBalance(BalanceDto balanceDto) {

        Balance balance = balanceRepository
                .findByAccountId(balanceDto.getAccountId()).orElseThrow(() -> new DataNotFoundException("Balance not found"));

        balance.setAuthorizationBalance(balanceDto.getAuthorizationBalance());
        balance.setCurrentBalance(balanceDto.getCurrentBalance());
        balanceRepository.save(balance);
        saveBalanceAudit(balance);
        return balanceMapper.toDto(balance);
    }

    @Transactional
    public BalanceAuditDto getBalanceAudit(long idAudit) {
        BalanceAudit balanceAudit = findEntityById(idAudit, balanceAuditRepository);
        return balanceAuditMapper.mapEntityToDto(balanceAudit);
    }

    private <T> T findEntityById(long id, CrudRepository<T, Long> repository) {
        Optional<T> entity = repository.findById(id);
        balanceServiceValidator.checkExistence(entity);
        return entity.get();
    }

    private void saveBalanceAudit(Balance balance) {
        BalanceAudit balanceAudit = balanceAuditMapper.mapBalanceToBalanceAudit(balance);
        balanceAuditRepository.save(balanceAudit);
    }
}
