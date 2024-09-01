package faang.school.accountservice.model;

import faang.school.accountservice.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "account_numbers_sequence")
public class AccountNumberSequence {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", length = 32, nullable = false)
    private AccountType accountType;
    @Column(name = "current_counter", nullable = false)
    private long currentCounter;

    @Transient
    private long initialValue;

}
