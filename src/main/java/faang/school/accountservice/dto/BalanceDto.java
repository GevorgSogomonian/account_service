package faang.school.accountservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceDto {
    private long id;
    private long accountId;
    private long authorizationAmount;
    private long actualAmount;
}