package faang.school.accountservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "free_account_numbers")
public class FreeAccountNumber {
    @EmbeddedId
    private FreeAccountId id;
}
