package faang.school.accountservice.exception;

import faang.school.accountservice.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleEntityNotFoundException(EntityNotFoundException e) {
        return new ErrorResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(IllegalEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleIllegalEntityException(IllegalEntityException e) {
        return new ErrorResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleUserNotFoundException(DataNotFoundException e) {
        return new ErrorResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }
}
