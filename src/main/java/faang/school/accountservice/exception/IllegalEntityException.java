package faang.school.accountservice.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IllegalEntityException extends RuntimeException {
    public IllegalEntityException(String message) {
        super(message);
        log.error(message, this);
    }
}
