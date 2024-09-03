package faang.school.accountservice.repository;

import faang.school.accountservice.model.AccountNumberSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountNumberSequenceRepository extends JpaRepository<AccountNumberSequence, Long> {
    @Query(nativeQuery = true, value = """
            UPDATE account_numbers_sequence SET current_counter = current_counter + 1
            WHERE account_type =: accountType
            RETURNING id, account_type, current_counter, version
            """)
    AccountNumberSequence incrementCounter(String accountType);

    @Query(nativeQuery = true, value = """
            UPDATE account_numbers_sequence SET current_counter = current_counter + :batchSize
            WHERE account_type = :accountType                    
            """)
    @Modifying
    void incrementCounterWithBatchSize(String accountType, int batchSize);

    @Query(nativeQuery = true, value = """
                    SELECT * FROM account_numbers_sequence WHERE account_type = :accountType
            """)
    AccountNumberSequence getByType(String accountType);
}
