package faang.school.accountservice.scheduler;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.service.FreeAccountNumbersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class AccountNumberScheduler {
    @Value("${account.number.batch.size")
    private int batchSize;
    private final FreeAccountNumbersService freeAccountNumbersService;

    @Scheduled(cron = "${account.number.cron.pattern}")
    public void generateAccountNumbers() {
        Arrays.stream(AccountType.values()).forEach(
                type -> freeAccountNumbersService.generatedAccountNumbers(type, batchSize));
    }
}
