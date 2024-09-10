package faang.school.accountservice.validator;

import faang.school.accountservice.client.ProjectServiceClient;
import faang.school.accountservice.client.UserServiceClient;
import faang.school.accountservice.dto.ProjectDto;
import faang.school.accountservice.dto.UserDto;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.exception.IllegalEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountValidatorTest {

    @Mock
    ProjectServiceClient projectServiceClient;

    @Mock
    UserServiceClient userServiceClient;

    @InjectMocks
    AccountValidator accountValidator;

    Long userId;
    Long projectId;
    UserDto userDto;
    ProjectDto projectDto;

    @BeforeEach
    void setUp() {
        userId = 1L;
        projectId = 1L;
        userDto = new UserDto();
        projectDto = new ProjectDto();
    }

    @Test
    @DisplayName("Should throw IllegalEntityException when both userId and projectId are null")
    void validateUserIdAndProjectId_bothUserIdAndProjectIdNull_throwsException() {
        assertThrows(IllegalEntityException.class, () -> accountValidator.validateUserIdAndProjectId(null, null));
    }

    @Test
    @DisplayName("Should throw IllegalEntityException when both userId and projectId are provided")
    void validateUserIdAndProjectId_bothUserIdAndProjectIdProvided_throwsException() {
        assertThrows(IllegalEntityException.class, () -> accountValidator.validateUserIdAndProjectId(userId, projectId));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when userId is provided but user does not exist")
    void validateUserIdAndProjectId_userIdProvidedAndUserDoesNotExist_throwsException() {
        when(userServiceClient.getUser(userId)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> accountValidator.validateUserIdAndProjectId(userId, null));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when projectId is provided but project does not exist")
    void validateUserIdAndProjectId_projectIdProvidedAndProjectDoesNotExist_throwsException() {
        when(projectServiceClient.getProject(projectId)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> accountValidator.validateUserIdAndProjectId(null, projectId));
    }

    @Test
    @DisplayName("Should not throw any exception when valid userId is provided")
    void validateUserIdAndProjectId_validUserId_noExceptionThrown() {
        when(userServiceClient.getUser(userId)).thenReturn(userDto);
        accountValidator.validateUserIdAndProjectId(userId, null);
    }

    @Test
    @DisplayName("Should not throw any exception when valid projectId is provided")
    void validateUserIdAndProjectId_validProjectId_noExceptionThrown() {
        when(projectServiceClient.getProject(projectId)).thenReturn(projectDto);
        accountValidator.validateUserIdAndProjectId(null, projectId);
    }
}