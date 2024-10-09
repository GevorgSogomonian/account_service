package faang.school.accountservice.repository;

import faang.school.accountservice.entity.BalanceAudit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceAuditRepository extends CrudRepository<BalanceAudit, Long> {

}
