package faang.school.accountservice.service;

import faang.school.accountservice.dto.BalanceAuditDto;
import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.entity.BalanceAudit;
import faang.school.accountservice.exception.DataNotFoundException;
import faang.school.accountservice.mapper.BalanceAuditMapper;
import faang.school.accountservice.mapper.BalanceMapperImpl;
import faang.school.accountservice.model.Account;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.repository.BalanceAuditRepository;
import faang.school.accountservice.repository.BalanceRepository;
import faang.school.accountservice.validator.BalanceServiceValidator;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Spy
    private BalanceMapperImpl balanceMapper;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private BalanceAuditRepository balanceAuditRepository;

    @Mock
    private BalanceAuditMapper balanceAuditMapper;

    @Mock
    private BalanceServiceValidator balanceServiceValidator;

    @InjectMocks
    private BalanceService balanceService;

    private BalanceDto balanceDto;
    private Account account;
    private Balance balance;
    private BalanceAudit balanceAudit;
    private long idAudit;
    private BalanceAuditDto balanceAuditDto;

    @BeforeEach
    public void setup() {
        idAudit = 1L;

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

        balanceAudit = BalanceAudit.builder()
                .id(1L)
                .accountId(account.getId())
                .transactionId(1L)
                .versionBalance(1L)
                .authorizationBalance(BigDecimal.ZERO)
                .currentBalance(BigDecimal.ZERO)
                .build();

        balanceAuditDto = BalanceAuditDto.builder().build();
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

    @Test
    @DisplayName("Test method getBalanceAudit")
    public void testGetBalanceAudit() {
        Optional<BalanceAudit> balanceAuditOptional = Optional.of(balanceAudit);
        when(balanceAuditRepository.findById(idAudit)).thenReturn(balanceAuditOptional);
        when(balanceAuditMapper.mapEntityToDto(balanceAudit)).thenReturn(balanceAuditDto);

        balanceService.getBalanceAudit(idAudit);

        verify(balanceAuditRepository, times(1)).findById(idAudit);
        verify(balanceServiceValidator, times(1)).checkExistence(balanceAuditOptional);
    }
}
