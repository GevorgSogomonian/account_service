package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "balanceId", source = "balance.id")
    @Mapping(target = "accountId", source = "account.id")
    AccountDto toDto(Account account);
    Account toEntity(AccountDto accountDto);
}
