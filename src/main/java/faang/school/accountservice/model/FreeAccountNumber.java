package faang.school.accountservice.model;

import faang.school.accountservice.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "free_account_numbers")
public class FreeAccountNumber {
    @Id
    @Column(name = "account_number", length = 16)
    private String accountNumber;
    @Column(name = "account_type", length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}
