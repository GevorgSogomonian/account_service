package faang.school.accountservice.service;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.model.Account;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.validator.AccountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

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
}