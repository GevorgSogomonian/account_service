package faang.school.accountservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {
    CREDIT("5236"),
    DEPOSIT("4200"),
    DEBIT("7230");

    private final String prefixCode;
}
