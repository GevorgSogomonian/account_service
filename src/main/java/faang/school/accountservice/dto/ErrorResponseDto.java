package faang.school.accountservice.dto;

public record ErrorResponseDto(
    String message,
    int code
) {
}
