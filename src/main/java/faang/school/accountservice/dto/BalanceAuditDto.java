package faang.school.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BalanceAuditDto {
    private long id;
    private long accountId;
    private long versionBalance;
    private BigDecimal authorizationBalance;
    private BigDecimal currentBalance;
    private long transactionId;
    private long balanceId;
    private LocalDateTime createdAt;
}
