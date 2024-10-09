package faang.school.accountservice.repository;

import faang.school.accountservice.model.FreeAccountId;
import faang.school.accountservice.model.FreeAccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeAccountNumbersRepository extends JpaRepository<FreeAccountNumber, FreeAccountId> {

    @Query(nativeQuery = true,
            value = """
                    DELETE FROM free_account_numbers fan
                    WHERE fan.account_type = :accountType 
                    AND fan.account_number = (
                    SELECT account_number FROM free_account_numbers
                    WHERE account_type = :accountType
                    ORDER BY account_number
                    LIMIT 1
                    ) 
                    returning account_type, account_number;
                    """)
    Optional<FreeAccountNumber> getFreeAccountNumber(String accountType);
}
