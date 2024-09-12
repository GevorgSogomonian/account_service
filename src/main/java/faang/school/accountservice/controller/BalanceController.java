package faang.school.accountservice.controller;

import faang.school.accountservice.dto.BalanceAuditDto;
import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping
    public BalanceDto createBalance(@RequestBody BalanceDto balanceDto) {
        return balanceService.createBalance(balanceDto.getAccountId());
    }

    @GetMapping("/{accountId}")
    public BalanceDto getBalance(@PathVariable long accountId) {
        return balanceService.getBalance(accountId);
    }

    @GetMapping("/audit/{idAudit}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceAuditDto getBalanceAuditDto(@PathVariable long idAudit) {
        return balanceService.getBalanceAudit(idAudit);
    }
}
