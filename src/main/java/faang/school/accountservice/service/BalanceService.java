package faang.school.accountservice.service;

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
        return balanceMapper.toDto(balance);
    }
}
