package faang.school.accountservice.model;

import faang.school.accountservice.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class FreeAccountId {
    @Column(name = "account_type", length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

}
