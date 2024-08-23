package faang.school.accountservice.service;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.enums.AccountStatus;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.model.Account;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.validator.AccountValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountValidator accountValidator;

    @Transactional(readOnly = true)
    public AccountDto getAccount(Long accountId) {
        val account = findAccountById(accountId);
        return accountMapper.toDto(account);
    }

    @Transactional
    public AccountDto openAccount(AccountDto accountDto) {
        accountValidator.validateUserIdAndProjectId(accountDto.getUserId(), accountDto.getProjectId());
        var account = accountMapper.toEntity(accountDto);
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Transactional
    public AccountDto freezeAccount(Long accountId) {
        var account = findAccountById(accountId);
        account.setAccountStatus(AccountStatus.FROZEN);
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Transactional
    public AccountDto closeAccount(Long accountId) {
        var account = findAccountById(accountId);
        account.setAccountStatus(AccountStatus.CLOSED);
        account.setClosedAt(LocalDateTime.now());
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    private Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId)
            .orElseThrow(() -> new EntityNotFoundException("Account with id: %d not found."
                .formatted(accountId)));
    }
}
