package faang.school.accountservice.service;

import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.entity.BalanceAudit;
import faang.school.accountservice.dto.BalanceAuditDto;
import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.mapper.BalanceAuditMapper;
import faang.school.accountservice.mapper.BalanceMapper;
import faang.school.accountservice.repository.BalanceAuditRepository;
import faang.school.accountservice.repository.BalanceRepository;
import faang.school.accountservice.validator.BalanceServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;
    private final BalanceServiceValidator balanceServiceValidator;
    private final BalanceAuditRepository balanceAuditRepository;
    private final BalanceAuditMapper balanceAuditMapper;

    @Transactional
    public BalanceDto getBalance(long idBalance) {
        Balance balance = findEntityById(idBalance, balanceRepository);
        return balanceMapper.mapBalanceToDto(balance);
    }

    @Transactional
    public BalanceDto createBalance(BalanceDto balanceDto) {
        Balance balance = balanceMapper.mapBalanceDtoToEntity(balanceDto);
        balance.setCreatedAt(LocalDateTime.now());
        balance.setUpdateAt(LocalDateTime.now());
        incrementVersionAccount(balance);

        balance = balanceRepository.save(balance);
        saveBalanceAudit(balance);

        return balanceMapper.mapBalanceToDto(balance);
    }

    @Transactional
    public BalanceDto updateBalance(BalanceDto balanceDto) {
        Balance balance = findEntityById(balanceDto.getId(), balanceRepository);
        balance.setAuthorizationAmount(balanceDto.getAuthorizationAmount());
        balance.setActualAmount(balanceDto.getActualAmount());
        incrementVersionAccount(balance);
        balance.setUpdateAt(LocalDateTime.now());

        balance = balanceRepository.save(balance);
        saveBalanceAudit(balance);

        return balanceMapper.mapBalanceToDto(balance);
    }

    @Transactional
    public BalanceAuditDto getBalanceAudit(long idAudit) {
        BalanceAudit balanceAudit = findEntityById(idAudit, balanceAuditRepository);
        return balanceAuditMapper.mapEntityToDto(balanceAudit);
    }

    private <T> T findEntityById(long id, CrudRepository<T, Long> repository) {
        Optional<T> entity = repository.findById(id);
        balanceServiceValidator.checkExistence(entity);
        return entity.get();
    }

    private void incrementVersionAccount(Balance balance) {
        long version = balance.getVersionBalance();
        balance.setVersionBalance(++version);
    }

    private void saveBalanceAudit(Balance balance) {
        BalanceAudit balanceAudit = balanceAuditMapper.mapBalanceToBalanceAudit(balance);
        long transactionId = balanceAudit.getTransactionId();
        balanceAudit.setTransactionId(++transactionId);
        balanceAuditRepository.save(balanceAudit);
    }
}
