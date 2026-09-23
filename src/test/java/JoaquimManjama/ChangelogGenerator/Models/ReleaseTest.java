package JoaquimManjama.ChangelogGenerator.Models;

import JoaquimManjama.ChangelogGenerator.Enums.ReleaseStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReleaseTest {

    @Test
    void testDefaultStatusIsDraft() {
        // Given
        Release release = new Release();

        // Then
        assertEquals(ReleaseStatus.DRAFT, release.getStatus());
    }

    @Test
    void testDefaultNumberOfCountsAreZero() {
        // Given
        Release release = new Release();

        // Then
        assertEquals(0, release.getNumberOfFeatures());
        assertEquals(0, release.getNumberOfFixes());
        assertEquals(0, release.getNumberOfImprovements());
    }

    @Test
    void testPrePersistSetsDates() {
        // Given
        Release release = new Release();
        release.setVersion("1.0");
        release.setDescription("Test");

        Project project = new Project();
        project.setId("proj-1");
        project.setName("Test");
        project.setSlug("test");
        project.setUser(new User("a@b.com", "p", "A", "B"));
        release.setProject(project);

        // When
        release.onCreate();

        // Then
        assertNotNull(release.getCreatedAt());
        assertNotNull(release.getReleaseDate());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Release release = new Release();
        String id = "release-1";
        String version = "1.0.0";
        String description = "Initial";
        LocalDateTime now = LocalDateTime.now();
        ReleaseStatus status = ReleaseStatus.PUBLISHED;

        // When
        release.setId(id);
        release.setVersion(version);
        release.setDescription(description);
        release.setCreatedAt(now);
        release.setReleaseDate(now);
        release.setStatus(status);
        release.setNumberOfFeatures(5);
        release.setNumberOfFixes(2);
        release.setNumberOfImprovements(3);

        // Then
        assertEquals(id, release.getId());
        assertEquals(version, release.getVersion());
        assertEquals(description, release.getDescription());
        assertEquals(now, release.getCreatedAt());
        assertEquals(now, release.getReleaseDate());
        assertEquals(status, release.getStatus());
        assertEquals(5, release.getNumberOfFeatures());
        assertEquals(2, release.getNumberOfFixes());
        assertEquals(3, release.getNumberOfImprovements());
    }
}
