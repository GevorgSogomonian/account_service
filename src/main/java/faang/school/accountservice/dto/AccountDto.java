package faang.school.accountservice.dto;


import faang.school.accountservice.enums.AccountStatus;
import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.enums.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Validated
public class AccountDto {
    @Positive
    private Long accountId;

    @NotNull
    private Long balanceId;

    @NotNull
    @Size(min = 12, max = 20)
    private String number;

    @Positive
    private Long userId;

    @Positive
    private Long projectId;

    @NotNull
    private AccountType accountType;

    @NotNull
    private Currency currency;

    @NotNull
    private AccountStatus accountStatus;
}
