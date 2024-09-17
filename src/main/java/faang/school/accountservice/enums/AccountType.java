package faang.school.accountservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {
    CREDIT(5236),
    DEPOSIT(4200),
    DEBIT(7230),
    CHECKING(8800),
    FOREIGN_CURRENCY(1231),
    BUSINESS(3340);

    private final int prefixCode;
}
