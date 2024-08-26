package faang.school.accountservice.service;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.exception.DataNotFoundException;
import faang.school.accountservice.mapper.BalanceMapperImpl;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.repository.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Spy
    private BalanceMapperImpl balanceMapper;

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private BalanceService balanceService;


    private BalanceDto balanceDto;
    private Account account;
    private Balance balance;



    @BeforeEach
    public void setup() {
        balanceDto = BalanceDto.builder()
                .accountId(1L)
                .authorizationBalance(BigDecimal.ZERO)
                .currentBalance(BigDecimal.ZERO)
                .build();

        account = Account.builder()
                .id(1L)
                .build();

        balance = Balance.builder()
                .id(1L)
                .account(account)
                .authorizationBalance(BigDecimal.ZERO)
                .currentBalance(BigDecimal.ZERO)
                .build();
    }

    @Test
    public void testCreateBalance_AccountFound() {
        when(accountRepository.findById(balanceDto.getAccountId())).thenReturn(Optional.of(account));
        when(balanceRepository.save(any(Balance.class))).thenReturn(balance);
        when(balanceMapper.toDto(balance)).thenReturn(balanceDto);

        BalanceDto result = balanceService.createBalance(balanceDto);

        assertEquals(balanceDto, result);
    }

    @Test
    public void testCreateBalance_AccountNotFound() {
        when(accountRepository.findById(balanceDto.getAccountId())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> balanceService.createBalance(balanceDto));
    }

    @Test
    @DisplayName("getBalance - success")
    public void testGetBalance() {
        when(balanceRepository.findById(1L)).thenReturn(Optional.of(balance));
        when(balanceMapper.toDto(balance)).thenReturn(balanceDto);

        BalanceDto result = balanceService.getBalance(1L);

        assertEquals(balanceDto, result);
    }

    @Test
    @DisplayName("getBalance - fail")
    public void testGetBalanceFail() {
        when(balanceRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> balanceService.getBalance(1L));
    }
    }
