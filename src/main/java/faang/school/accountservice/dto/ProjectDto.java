package faang.school.accountservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private long ownerId;
    private long parentProjectId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
