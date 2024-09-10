package faang.school.accountservice.service;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.exception.DataNotFoundException;
import faang.school.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final BalanceService balanceService;
    private final AccountRepository accountRepository;

    @Transactional
    public BalanceDto createBalance(Long accountId) {
        return balanceService.createBalance(accountId);
    }

    @Transactional(readOnly = true)
    public BalanceDto getBalance(long accountId) {
        return balanceService.getBalance(accountId);
    }

    @Transactional
    public BalanceDto increaseBalance(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account with id " + accountId + " not found"));
        Balance balance = account.getBalance();
        return balanceService.increaseBalance(balance, amount);
    }

    @Transactional
    public BalanceDto reduceBalance(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account with id " + accountId + " not found"));
        Balance balance = account.getBalance();
        return balanceService.reduceBalance(balance, amount);
    }

}
