package faang.school.accountservice.service;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.AccountNumberSequence;
import faang.school.accountservice.model.FreeAccountId;
import faang.school.accountservice.model.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumberSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class FreeAccountNumbersService {
    private final FreeAccountNumbersRepository freeAccountNumbersRepository;
    private final AccountNumberSequenceRepository accountNumberSequenceRepository;

    private long accountNumberPattern = 1000_0000_0000_0000L;

    @Transactional
    public void generatedAccountNumbers(AccountType type, int batchSize) {
        List<FreeAccountNumber> freeAccountNumbers = new ArrayList<>();
        long typeNumber = accountNumberPattern / 1000 * type.getPrefixCode();
        long  startCounter = accountNumberSequenceRepository.findByAccountType(type.name()).getCurrentCounter();
        long endCounter = accountNumberSequenceRepository.incrementCounterWithBatchSize(type.name(),batchSize).getCurrentCounter();
        for (long i = startCounter; i < endCounter; i++) {
            freeAccountNumbers.add(new FreeAccountNumber(new FreeAccountId(type, typeNumber + i)));
        }
        freeAccountNumbersRepository.saveAll(freeAccountNumbers);
    }

    @Transactional
    public void retrieveAccountNumber(AccountType type, Consumer<FreeAccountNumber> consumer) {
        if (!freeAccountNumbersRepository.getFreeAccountNumber(type.name()).isPresent()) {
            long freeNumber = accountNumberSequenceRepository.incrementCounter(type.name()).getCurrentCounter();
            freeAccountNumbersRepository.save(new FreeAccountNumber(new FreeAccountId(type, freeNumber)));
        }
        consumer.accept(freeAccountNumbersRepository.getFreeAccountNumber(type.name()).get());
    }
}
