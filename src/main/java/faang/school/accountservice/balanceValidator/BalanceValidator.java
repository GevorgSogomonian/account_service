package faang.school.accountservice.balanceValidator;

import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class BalanceValidator {

    public static final BigDecimal MAX_VALUE_AMOUNT_FOR_REPLENISHMENT = BigDecimal.valueOf(10000);

    public void isBalanceValid(Balance balance) {
        if (Objects.isNull(balance)) {
            throw new IllegalArgumentException("Balance cannot be null");
        }
    }

    public void isBalanceValidForWithdrawal (Balance balance, BigDecimal amount) {
        if (balance.getAuthorizationBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Check your balance");
        }
    }

    public void isAmountValidForReplenishment (BigDecimal amount) {
        if (amount.compareTo(MAX_VALUE_AMOUNT_FOR_REPLENISHMENT) > 0) {
            throw new IllegalArgumentException("Amount cannot be greater than " + MAX_VALUE_AMOUNT_FOR_REPLENISHMENT);
        }
    }

    public void checkExistBalanceForAccount(Account account) {
        if (Objects.nonNull(account.getBalance())) {
            throw new IllegalArgumentException("Balance already exist");
        }
    }
}
