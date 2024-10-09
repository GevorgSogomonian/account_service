package faang.school.accountservice.model;

import faang.school.accountservice.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OptimisticLocking;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@OptimisticLocking
@Table(name = "account_numbers_sequence")
public class AccountNumberSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", length = 32, nullable = false)
    private AccountType accountType;
    @Column(name = "current_counter", nullable = false)
    private long currentCounter;
    @Version
    private long version;
}
