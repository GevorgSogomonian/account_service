package faang.school.accountservice.mapper;

import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.entity.BalanceAudit;
import faang.school.accountservice.dto.BalanceAuditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BalanceAuditMapper {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "version", target = "versionBalance")
    BalanceAudit mapBalanceToBalanceAudit(Balance balance);

    @Mapping(source = "balance.id", target = "balanceId")
    BalanceAuditDto mapEntityToDto(BalanceAudit balanceAudit);
}