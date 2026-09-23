package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.ProjectDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ProjectRequestDTO;
import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    private ProjectService projectService;
    private ProjectRepository repository;
    private User user;

    @BeforeEach
    void setUp() {
        repository = mock(ProjectRepository.class);
        projectService = new ProjectService();
        ReflectionTestUtils.setField(projectService, "repository", repository);
        user = new User("test@example.com", "pass", "Test", "User");
        user.setId("user-1");
    }

    @Test
    void testAddProject() {
        // Given
        ProjectRequestDTO request = new ProjectRequestDTO("My Project", "my-project", "org/repo");
        Project savedProject = new Project();
        savedProject.setId("project-1");
        savedProject.setName("My Project");
        savedProject.setSlug("my-project");
        savedProject.setGithubRepo("org/repo");
        savedProject.setUser(user);

        when(repository.save(any(Project.class))).thenReturn(savedProject);

        // When
        ProjectDTO result = projectService.addProject(user, request);

        // Then
        assertNotNull(result);
        assertEquals("My Project", result.name());
        assertEquals("my-project", result.slug());
        assertEquals("org/repo", result.githubRepo());
        verify(repository, times(1)).save(any(Project.class));
    }

    @Test
    void testGetProjectFound() {
        // Given
        Project project = new Project();
        project.setId("project-1");
        project.setName("My Project");
        project.setSlug("my-project");
        project.setGithubRepo("org/repo");
        project.setUser(user);

        when(repository.findById("project-1")).thenReturn(Optional.of(project));

        // When
        ProjectDTO result = projectService.getProject("project-1");

        // Then
        assertNotNull(result);
        assertEquals("My Project", result.name());
    }

    @Test
    void testGetProjectNotFound() {
        // Given
        when(repository.findById("nonexistent")).thenReturn(Optional.empty());

        // When
        ProjectDTO result = projectService.getProject("nonexistent");

        // Then
        assertNull(result);
    }

    @Test
    void testUpdateProjectFound() {
        // Given
        Project existing = new Project();
        existing.setId("project-1");
        existing.setName("Old Name");
        existing.setSlug("old-slug");
        existing.setGithubRepo("old/repo");
        existing.setUser(user);

        when(repository.findById("project-1")).thenReturn(Optional.of(existing));

        Project updatedProject = new Project();
        updatedProject.setId("project-1");
        updatedProject.setName("New Name");
        updatedProject.setSlug("new-slug");
        updatedProject.setGithubRepo("new/repo");
        updatedProject.setUser(user);

        when(repository.save(any(Project.class))).thenReturn(updatedProject);

        ProjectRequestDTO request = new ProjectRequestDTO("New Name", "new-slug", "new/repo");

        // When
        ProjectDTO result = projectService.updateProject(request, "project-1");

        // Then
        assertNotNull(result);
        verify(repository, times(1)).save(any(Project.class));
    }

    @Test
    void testUpdateProjectNotFound() {
        // Given
        when(repository.findById("nonexistent")).thenReturn(Optional.empty());

        ProjectRequestDTO request = new ProjectRequestDTO("Name", "slug", "repo");

        // When
        ProjectDTO result = projectService.updateProject(request, "nonexistent");

        // Then
        assertNull(result);
    }

    @Test
    void testDeleteProjectFound() {
        // Given
        Project project = new Project();
        project.setId("project-1");
        project.setName("My Project");
        project.setSlug("my-project");
        project.setGithubRepo("org/repo");
        project.setUser(user);

        when(repository.findById("project-1")).thenReturn(Optional.of(project));

        // When
        ProjectDTO result = projectService.deleteProject("project-1");

        // Then
        assertNotNull(result);
        verify(repository, times(1)).deleteById("project-1");
    }

    @Test
    void testDeleteProjectNotFound() {
        // Given
        when(repository.findById("nonexistent")).thenReturn(Optional.empty());

        // When
        ProjectDTO result = projectService.deleteProject("nonexistent");

        // Then
        assertNull(result);
    }

    @Test
    void testConvertToDTO() {
        // Given
        Project project = new Project();
        project.setId("project-1");
        project.setName("My Project");
        project.setSlug("my-project");
        project.setGithubRepo("org/repo");

        // When
        ProjectDTO dto = projectService.convertToDTO(project);

        // Then
        assertEquals("project-1", dto.id());
        assertEquals("My Project", dto.name());
        assertEquals("my-project", dto.slug());
        assertEquals("org/repo", dto.githubRepo());
    }
}
