package faang.school.accountservice.controller;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BalanceDto createNewAccountBalance(@PathVariable Long accountId) {
        return accountService.createBalance(accountId);
    }

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDto getAccountBalance(@PathVariable long accountId) {
        return accountService.getBalance(accountId);
    }

    @PutMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDto updateAccountBalance(@PathVariable Long accountId,
                                           @RequestParam BigDecimal amount,
                                           @RequestParam Boolean isReplenishment) {
        return accountService.updateBalance(accountId, amount, isReplenishment);
    }
}
