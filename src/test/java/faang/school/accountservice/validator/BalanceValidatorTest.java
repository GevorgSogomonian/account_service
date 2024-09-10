package faang.school.accountservice.validator;

import faang.school.accountservice.balanceValidator.BalanceValidator;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
public class BalanceValidatorTest {

    @InjectMocks
    private BalanceValidator balanceValidator;

    @Test
    public void testIsBalanceValid_NullBalance_ThrowsIllegalArgumentException() {
        // Arrange
        Balance balance = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> balanceValidator.isBalanceValid(balance));
    }

    @Test
    @DisplayName("isBalanceValid - balance not null")
    public void testIsBalanceValid_ValidBalance_NoException() {
        Balance balance = new Balance();

        balanceValidator.isBalanceValid(balance);
    }

    @Test
    @DisplayName("isBalanceValid - authorization balance less amount")
    public void testIsBalanceValidForWithdrawal_InsufficientBalance() {
        Balance balance = new Balance();
        balance.setAuthorizationBalance(BigDecimal.ZERO);
        BigDecimal amount = BigDecimal.ONE;

        assertThrows(IllegalArgumentException.class, () -> balanceValidator.isBalanceValidForWithdrawal(balance, amount));
    }

    @Test
    @DisplayName("isBalanceValid - authorization balance more amount")
    public void testIsBalanceValidForWithdrawal_SufficientBalance_NoException() {
        Balance balance = new Balance();
        balance.setAuthorizationBalance(BigDecimal.ONE);
        BigDecimal amount = BigDecimal.ZERO;

        balanceValidator.isBalanceValidForWithdrawal(balance, amount);
    }

    @Test
    @DisplayName("isAmountValidForReplenishment - amount greater than max value")
    public void testIsAmountValidForReplenishment_AmountGreaterThanMaxValue_ThrowsIllegalArgumentException() {
        BigDecimal amount = BigDecimal.valueOf(10001);

        assertThrows(IllegalArgumentException.class, () -> balanceValidator.isAmountValidForReplenishment(amount));
    }

    @Test
    @DisplayName("isAmountValidForReplenishment - amount less than or equal to max value")
    public void testIsAmountValidForReplenishment_AmountLessThanOrEqualToMaxValue_NoException() {
        BigDecimal amount = BigDecimal.valueOf(10000);

        balanceValidator.isAmountValidForReplenishment(amount);
    }

    @Test
    @DisplayName("checkExistBalanceForAccount - balance already exists")
    public void testCheckExistBalanceForAccount_BalanceAlreadyExists_ThrowsIllegalArgumentException() {
        Account account = new Account();
        account.setBalance(new Balance());

        assertThrows(IllegalArgumentException.class, () -> balanceValidator.checkExistBalanceForAccount(account));
    }

    @Test
    @DisplayName("checkExistBalanceForAccount - balance not exists")
    public void testCheckExistBalanceForAccount_NoBalanceExists_NoException() {
        Account account = new Account();

        balanceValidator.checkExistBalanceForAccount(account);
    }
}
