package faang.school.accountservice.repository;

import faang.school.accountservice.model.AccountNumberSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountNumberSequenceRepository extends JpaRepository<AccountNumberSequence, String> {
    @Query(nativeQuery = true, value = """
            UPDATE account_numbers_sequence SET current_counter = current_counter + 1
            WHERE account_type =: accountType,
            RETURNING account_type, current_counter
            """)
    @Modifying
    AccountNumberSequence getAccountNumberSequence(String accountType);


    @Query(nativeQuery = true, value = """
            UPDATE account_numbers_sequence SET current_counter = current_counter + :batchSize
            WHERE account_type = :accountType
            RETURNING account_type, current_counter, old.current_counter AS initialValue
            """)
    AccountNumberSequence incrementCounterWithBatchSize(String accountType, int batchSize);
}
