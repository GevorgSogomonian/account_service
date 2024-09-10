package faang.school.accountservice.service;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.exception.DataNotFoundException;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.model.Account;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.validator.AccountValidator;
import org.junit.jupiter.api.BeforeEach;
import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.exception.DataNotFoundException;
import faang.school.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private BalanceService balanceService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountMapper accountMapper;

    @Mock
    AccountValidator accountValidator;

    @InjectMocks
    AccountService accountService;

    Long accountId;
    AccountDto accountDto;
    Account account;
    Long userId;
    Long projectId;

    @BeforeEach
    void setUp() {
        accountId = 1L;
        userId = 1L;
        projectId = 1L;
        accountDto = AccountDto.builder()
            .userId(userId)
            .projectId(projectId)
            .build();
        account = new Account();
    }

    @Test
    @DisplayName("Should return account when account ID is found")
    void getAccount() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.getAccount(accountId);

        verify(accountRepository).findById(accountId);
        verify(accountMapper).toDto(account);
        assertNotNull(result);
        assertEquals(accountDto, result);
    }

    @Test
    @DisplayName("Should open a new account and return account DTO")
    void openAccount() {
        when(accountMapper.toEntity(accountDto)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.openAccount(accountDto);

        verify(accountValidator).validateUserIdAndProjectId(userId, projectId);
        verify(accountMapper).toEntity(accountDto);
        verify(accountRepository).save(account);
        verify(accountMapper).toDto(account);
        assertNotNull(result);
        assertEquals(accountDto, result);
    }

    @Test
    @DisplayName("Should freeze account and return updated account DTO")
    void freezeAccount() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.freezeAccount(accountId);

        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
        verify(accountMapper).toDto(account);
        assertNotNull(result);
        assertEquals(accountDto, result);
    }

    @Test
    @DisplayName("Should close account and return updated account DTO")
    void closeAccount() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.closeAccount(accountId);

        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
        verify(accountMapper).toDto(account);
        assertNotNull(result);
        assertEquals(accountDto, result);
    }

    @Test
    @DisplayName("createBalance - verify")
    public void verifyCreateBalance() {
        long accountId = 1L;

        accountService.createBalance(accountId);
        verify(balanceService, times(1)).createBalance(accountId);
    }

    @Test
    @DisplayName("getBalance - success")
    public void testGetBalance() {
        long accountId = 1L;

        accountService.getBalance(accountId);
        verify(balanceService, times(1)).getBalance(accountId);
    }

    @Test
    @DisplayName("getBalance - success")
    public void testUpdateBalance_AccountFound() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Account account = new Account();
        Balance balance = new Balance();
        account.setBalance(balance);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        BalanceDto balanceDto = new BalanceDto();
        when(balanceService.increaseBalance(balance, amount)).thenReturn(balanceDto);

        BalanceDto result = accountService.increaseBalance(accountId, amount);

        assertEquals(balanceDto, result);
        verify(accountRepository, times(1)).findById(accountId);
        verify(balanceService, times(1)).increaseBalance(balance, amount);
    }

    @Test
    @DisplayName("getBalance - fail")
    public void testUpdateBalance_AccountNotFound() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> accountService.increaseBalance(accountId, amount));
        verify(accountRepository, times(1)).findById(accountId);
        verify(balanceService, never()).increaseBalance(any(), any());
    }

    @Test
    @DisplayName("reduceBalance - success")
    public void testReduceBalance_AccountFound() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Account account = new Account();
        Balance balance = new Balance();
        account.setBalance(balance);
        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(account));
        BalanceDto balanceDto = new BalanceDto();
        when(balanceService.reduceBalance(balance, amount)).thenReturn(balanceDto);

        BalanceDto result = accountService.reduceBalance(accountId, amount);

        assertEquals(balanceDto, result);
    }

    @Test
    @DisplayName("reduceBalance - fail")
    public void testReduceBalance_AccountNotFound() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.empty());

        assertThrows(DataNotFoundException.class, () -> accountService.reduceBalance(accountId, amount));
    }
}