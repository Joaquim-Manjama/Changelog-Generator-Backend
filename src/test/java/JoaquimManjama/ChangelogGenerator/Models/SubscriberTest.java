package JoaquimManjama.ChangelogGenerator.Models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberTest {

    @Test
    void testPrePersistSetsCreatedAt() {
        // Given
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail("sub@example.com");

        Project project = new Project();
        project.setId("proj-1");
        project.setName("Test");
        project.setSlug("test");
        project.setUser(new User("a@b.com", "p", "A", "B"));
        subscriber.setProject(project);

        // When
        subscriber.onCreate();

        // Then
        assertNotNull(subscriber.getCreatedAt());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Subscriber subscriber = new Subscriber();
        String id = "sub-1";
        String email = "sub@example.com";
        LocalDateTime now = LocalDateTime.now();

        Project project = new Project();
        project.setId("proj-1");
        project.setName("Test");
        project.setSlug("test");
        project.setUser(new User("a@b.com", "p", "A", "B"));

        // When
        subscriber.setId(id);
        subscriber.setEmail(email);
        subscriber.setProject(project);
        subscriber.setCreatedAt(now);

        // Then
        assertEquals(id, subscriber.getId());
        assertEquals(email, subscriber.getEmail());
        assertEquals(project, subscriber.getProject());
        assertEquals(now, subscriber.getCreatedAt());
    }
}
