package faang.school.accountservice.service;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.AccountNumberSequence;
import faang.school.accountservice.model.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumberSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FreeAccountNumberServiceTest {
    @Mock
    private FreeAccountNumbersRepository freeAccountNumbersRepository;
    @Mock
    private AccountNumberSequenceRepository accountNumberSequenceRepository;
    @Spy
    @InjectMocks
    private FreeAccountNumbersService freeAccountNumbersService;

    @Test
    void generatedAccountNumbersTest() {
        int batchSize = 10;
        AccountType type = AccountType.CREDIT;
        long typeNumber = 5236_0000_0000_0000l;
        AccountNumberSequence accountNumberSequence = AccountNumberSequence.builder().currentCounter(1L).build();
        when(freeAccountNumbersService.getTypeNumber(type)).thenReturn(typeNumber);
        when(accountNumberSequenceRepository.getByType(any())).thenReturn(accountNumberSequence);
        freeAccountNumbersService.generatedAccountNumbers(type, batchSize);
        verify(accountNumberSequenceRepository, times(1)).getByType(type.name());
        verify(accountNumberSequenceRepository, times(1)).incrementCounterWithBatchSize(type.name(), batchSize);
        verify(freeAccountNumbersRepository, times(1)).saveAll(anyList());
    }
}
