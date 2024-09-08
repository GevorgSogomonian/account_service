package faang.school.accountservice.service;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private BalanceService balanceService;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("createBalance - verify")
    public void verifyCreateBalance() {
        long accountId = 1L;

        BalanceDto result = accountService.createBalance(accountId);
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
        Boolean isReplenishment = true;
        Account account = new Account();
        Balance balance = new Balance();
        account.setBalance(balance);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        BalanceDto balanceDto = new BalanceDto();
        when(balanceService.updateBalance(balance, amount, isReplenishment)).thenReturn(balanceDto);

        BalanceDto result = accountService.updateBalance(accountId, amount, isReplenishment);

        assertEquals(balanceDto, result);
        verify(accountRepository, times(1)).findById(accountId);
        verify(balanceService, times(1)).updateBalance(balance, amount, isReplenishment);
    }

    @Test
    @DisplayName("getBalance - fail")
    public void testUpdateBalance_AccountNotFound() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Boolean isReplenishment = true;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> accountService.updateBalance(accountId, amount, isReplenishment));
        verify(accountRepository, times(1)).findById(accountId);
        verify(balanceService, never()).updateBalance(any(), any(), any());
    }
}
