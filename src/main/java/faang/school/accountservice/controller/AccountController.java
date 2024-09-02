package faang.school.accountservice.controller;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.service.AccountService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getAccount(@Positive @PathVariable Long accountId) {
        return accountService.getAccount(accountId);
    }

    @PostMapping("/open")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto openAccount(@Validated @RequestBody AccountDto accountDto) {
        return accountService.openAccount(accountDto);
    }

    @PutMapping("/{accountId}/freeze")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto freezeAccount(@Positive @PathVariable Long accountId) {
        return accountService.freezeAccount(accountId);
    }

    @PutMapping("/{accountId}/close")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto closeAccount(@Positive @PathVariable Long accountId) {
        return accountService.closeAccount(accountId);
    }
}
