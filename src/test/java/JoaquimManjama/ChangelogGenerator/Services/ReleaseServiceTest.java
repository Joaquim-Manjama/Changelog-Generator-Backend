package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.ReleaseDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ReleaseRequestDTO;
import JoaquimManjama.ChangelogGenerator.Enums.ReleaseStatus;
import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.Release;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.ProjectRepository;
import JoaquimManjama.ChangelogGenerator.Repositories.ReleaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReleaseServiceTest {

    private ReleaseService releaseService;
    private ProjectRepository projectRepository;
    private ReleaseRepository repository;
    private User user;

    @BeforeEach
    void setUp() {
        projectRepository = mock(ProjectRepository.class);
        repository = mock(ReleaseRepository.class);
        releaseService = new ReleaseService();
        ReflectionTestUtils.setField(releaseService, "projectRepository", projectRepository);
        ReflectionTestUtils.setField(releaseService, "repository", repository);
        user = new User("test@example.com", "pass", "Test", "User");
        user.setId("user-1");
    }

    @Test
    void testCreateRelease_Success() {
        // Given
        Project project = new Project();
        project.setId("proj-1");
        project.setName("My Project");
        project.setSlug("my-project");
        project.setUser(user);

        ReleaseRequestDTO request = new ReleaseRequestDTO("proj-1", "1.0.0", "Initial release");

        when(projectRepository.findById("proj-1")).thenReturn(Optional.of(project));

        Release savedRelease = new Release();
        savedRelease.setId("release-1");
        savedRelease.setVersion("1.0.0");
        savedRelease.setDescription("Initial release");
        savedRelease.setProject(project);
        savedRelease.setStatus(ReleaseStatus.DRAFT);
        savedRelease.setCreatedAt(LocalDateTime.now());
        savedRelease.setReleaseDate(LocalDateTime.now());
        savedRelease.setNumberOfFeatures(0);
        savedRelease.setNumberOfFixes(0);
        savedRelease.setNumberOfImprovements(0);

        when(repository.save(any(Release.class))).thenReturn(savedRelease);

        // When
        ReleaseDTO result = releaseService.createRelease(request);

        // Then
        assertNotNull(result);
        assertEquals("1.0.0", result.version());
        assertEquals("Initial release", result.description());
        assertEquals(ReleaseStatus.DRAFT.toString(), result.status());
    }

    @Test
    void testCreateRelease_ProjectNotFound() {
        // Given
        ReleaseRequestDTO request = new ReleaseRequestDTO("nonexistent", "1.0.0", "desc");

        when(projectRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // When
        ReleaseDTO result = releaseService.createRelease(request);

        // Then
        assertNull(result);
    }

    @Test
    void testToggleReleaseStatus_DraftToPublished() {
        // Given
        Release release = new Release();
        release.setId("release-1");
        release.setVersion("1.0.0");
        release.setProject(new Project());
        release.setStatus(ReleaseStatus.DRAFT);
        release.setCreatedAt(LocalDateTime.now());
        release.setReleaseDate(LocalDateTime.now());
        release.setNumberOfFeatures(0);
        release.setNumberOfFixes(0);
        release.setNumberOfImprovements(0);

        when(repository.findById("release-1")).thenReturn(Optional.of(release));
        when(repository.save(any(Release.class))).thenReturn(release);

        // When
        ReleaseDTO result = releaseService.toggleReleaseStatus("release-1");

        // Then
        assertNotNull(result);
        assertEquals(ReleaseStatus.PUBLISHED.toString(), result.status());
    }

    @Test
    void testToggleReleaseStatus_PublishedToDraft() {
        // Given
        Release release = new Release();
        release.setId("release-1");
        release.setVersion("1.0.0");
        release.setProject(new Project());
        release.setStatus(ReleaseStatus.PUBLISHED);
        release.setCreatedAt(LocalDateTime.now());
        release.setReleaseDate(LocalDateTime.now());
        release.setNumberOfFeatures(5);
        release.setNumberOfFixes(2);
        release.setNumberOfImprovements(3);

        when(repository.findById("release-1")).thenReturn(Optional.of(release));
        when(repository.save(any(Release.class))).thenReturn(release);

        // When
        ReleaseDTO result = releaseService.toggleReleaseStatus("release-1");

        // Then
        assertNotNull(result);
        assertEquals(ReleaseStatus.DRAFT.toString(), result.status());
    }

    @Test
    void testToggleReleaseStatus_NotFound() {
        // Given
        when(repository.findById("nonexistent")).thenReturn(Optional.empty());

        // When
        ReleaseDTO result = releaseService.toggleReleaseStatus("nonexistent");

        // Then
        assertNull(result);
    }

}
