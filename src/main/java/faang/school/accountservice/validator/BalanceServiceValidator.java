package faang.school.accountservice.validator;

import faang.school.accountservice.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BalanceServiceValidator {

    public <T> void checkExistence(Optional<T> entity) {
        if(entity.isEmpty()) {
            throw new NotFoundException("Balance not found with this index.");
        }
    }
}
