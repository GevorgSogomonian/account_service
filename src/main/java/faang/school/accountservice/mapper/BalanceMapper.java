package faang.school.accountservice.mapper;

import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.dto.BalanceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BalanceMapper {

    @Mapping(source = "account.id", target = "accountId")
    BalanceDto mapBalanceToDto(Balance balance);

    @Mapping(source = "accountId", target = "account.id")
    Balance mapBalanceDtoToEntity(BalanceDto balanceDto);
}
