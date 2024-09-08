package faang.school.accountservice.service;

import faang.school.accountservice.balanceValidator.BalanceValidator;
import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.exception.DataNotFoundException;
import faang.school.accountservice.mapper.BalanceMapper;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final AccountRepository accountRepository;
    private final BalanceMapper balanceMapper;
    private final BalanceRepository balanceRepository;
    private final BalanceValidator balanceValidator;

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
        return balanceMapper.toDto(savedBalance);
    }

    @Transactional(readOnly = true)
    public BalanceDto getBalance(long accountId) {
        Balance balance = getBalanceByAccountId(accountId);
        return balanceMapper.toDto(balance);
    }

    @Transactional
    public BalanceDto updateBalance(Balance balance, BigDecimal amount, Boolean isReplenishment) {

        balanceValidator.isBalanceValid(balance);

        if (isReplenishment) {
            balanceValidator.isAmountValidForReplenishment(amount);
            balance.setAuthorizationBalance(balance.getAuthorizationBalance().add(amount));
        } else {
            balanceValidator.isBalanceValidForWithdrawal(balance, amount);
            balance.setAuthorizationBalance(balance.getAuthorizationBalance().subtract(amount));
        }
        balanceRepository.save(balance);
        return balanceMapper.toDto(balance);
    }

    private Balance getBalanceByAccountId(Long accountId) {
        return balanceRepository.findByAccountId(accountId)
                .orElseThrow(() -> new DataNotFoundException("Balance not found for account id: " + accountId));
    }
}
