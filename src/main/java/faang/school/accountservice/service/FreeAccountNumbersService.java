package faang.school.accountservice.service;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.FreeAccountId;
import faang.school.accountservice.model.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumberSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class FreeAccountNumbersService {
    private final FreeAccountNumbersRepository freeAccountNumbersRepository;
    private final AccountNumberSequenceRepository accountNumberSequenceRepository;

    private long accountNumberPattern = 1000_0000_0000_0000L;

    @Transactional
    public void generatedAccountNumbers(AccountType type, int batchSize) {
        List<FreeAccountNumber> freeAccountNumbers = new ArrayList<>();
        long typeNumber = getTypeNumber(type);
        long start = accountNumberSequenceRepository.getByType(type.name()).getCurrentCounter();
        accountNumberSequenceRepository.incrementCounterWithBatchSize(type.name(), batchSize);
        log.info("Create new numbers in quantity{}", batchSize);
        long end = batchSize + start;
        for (long i = start; i < end; i++) {
            freeAccountNumbers.add(FreeAccountNumber.builder().
                    id(FreeAccountId.builder().
                            accountType(type).accountNumber(typeNumber + i)
                            .build())
                    .build());
        }
        freeAccountNumbersRepository.saveAll(freeAccountNumbers);
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(delay = 3000L))
    public void retrieveAccountNumber(AccountType type, Consumer<FreeAccountNumber> consumer) {
        Optional<FreeAccountNumber> freeAccountNumber = freeAccountNumbersRepository.getFreeAccountNumber(type.name());
        if (!freeAccountNumber.isPresent()) {
            long freeNumber = accountNumberSequenceRepository.incrementCounter(type.name()).getCurrentCounter()-1;
            log.info("Create new number {}", freeNumber);
            freeAccountNumbersRepository.save(FreeAccountNumber.builder()
                    .id(FreeAccountId.builder()
                            .accountType(type)
                            .accountNumber(getTypeNumber(type) + freeNumber)
                            .build())
                    .build());
            FreeAccountNumber newNumber = freeAccountNumbersRepository.getFreeAccountNumber(type.name()).get();
            consumer.accept(newNumber);
            log.info("Generated new number with type {} and number {}",
                    newNumber.getId().getAccountType(), newNumber.getId().getAccountNumber());
        } else {
            consumer.accept(freeAccountNumber.get());
        }
    }

    private long getTypeNumber(AccountType type) {
        int prefixDigitDivider = 1000;
        return accountNumberPattern / prefixDigitDivider * type.getPrefixCode();
    }
}
