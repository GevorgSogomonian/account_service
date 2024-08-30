package faang.school.accountservice.validator;

import faang.school.accountservice.client.ProjectServiceClient;
import faang.school.accountservice.client.UserServiceClient;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.exception.IllegalEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountValidator {
    private final ProjectServiceClient projectServiceClient;
    private final UserServiceClient userServiceClient;

    public void validateUserIdAndProjectId(Long userId, Long projectId) {
        if (userId == null && projectId == null) {
            throw new IllegalEntityException("Either userId or projectId must be provided.");
        }
        if (userId != null && projectId != null) {
            throw new IllegalEntityException("Only one of userId or projectId can be provided, not both.");
        }
        if (userId != null && userServiceClient.getUser(userId) == null) {
            throw new EntityNotFoundException("User with ID " + userId + " does not exist.");
        }
        if (projectId != null && projectServiceClient.getProject(projectId) == null) {
            throw new EntityNotFoundException("Project with ID " + projectId + " does not exist.");
        }
    }
}
