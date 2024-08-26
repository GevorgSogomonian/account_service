package faang.school.accountservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BalanceAuditDto {
    private long id;
    private long accountId;
    private long versionBalance;
    private long authorizationAmount;
    private long actualAmount;
    private long transactionId;
    private long balanceId;
    private LocalDateTime createdAt;
}
