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
    @Value("${account.number.pattern}")
    private  long accountNumberPattern;

    @Transactional
    public void generatedAccountNumbers(AccountType type, int batchSize) {
        List<FreeAccountNumber> freeAccountNumbers = new ArrayList<>();
        long typeNumber = accountNumberPattern * type.getPrefixCode();
        AccountNumberSequence period = accountNumberSequenceRepository.incrementCounterWithBatchSize(type.name(), batchSize);
        for (long i = period.getInitialValue(); i < period.getCurrentCounter(); i++) {
            freeAccountNumbers.add(new FreeAccountNumber(new FreeAccountId(type, typeNumber + i)));
        }
        freeAccountNumbersRepository.saveAll(freeAccountNumbers);
    }

    @Transactional
    public void retrieveAccountNumber(AccountType type, Consumer<FreeAccountNumber> consumer) {
        if (!freeAccountNumbersRepository.getFreeAccountNumber(type.name()).isPresent()) {
            generatedAccountNumbers(type, 1);
        }
        consumer.accept(freeAccountNumbersRepository.getFreeAccountNumber(type.name()).get());
    }
}
