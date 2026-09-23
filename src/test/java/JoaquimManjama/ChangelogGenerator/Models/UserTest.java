package JoaquimManjama.ChangelogGenerator.Models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructor() {
        // Given
        String email = "test@example.com";
        String password = "encryptedPass";
        String firstName = "Test";
        String lastName = "User";

        // When
        User user = new User(email, password, firstName, lastName);

        // Then
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
    }

    @Test
    void testGetUsernameReturnsEmail() {
        // Given
        User user = new User("test@example.com", "pass", "Test", "User");

        // Then
        assertEquals("test@example.com", user.getUsername());
    }

    @Test
    void testDefaultGithubConnectedIsFalse() {
        // Given
        User user = new User();

        // Then
        assertFalse(user.getGithubConnected());
    }

    @Test
    void testDefaultProjectsIsEmptyList() {
        // Given
        User user = new User();

        // Then
        assertNotNull(user.getProjects());
        assertTrue(user.getProjects().isEmpty());
    }

    @Test
    void testGetAuthoritiesReturnsEmptyList() {
        // Given
        User user = new User("a@b.com", "p", "A", "B");

        // Then
        assertNotNull(user.getAuthorities());
        assertTrue(user.getAuthorities().isEmpty());
    }

    @Test
    void testDefaultUserDetailsMethodsReturnTrue() {
        // Given
        User user = new User("a@b.com", "p", "A", "B");

        // Then
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }

    @Test
    void testPrePersistSetsCreatedAt() {
        // Given
        User user = new User();
        user.setEmail("a@b.com");
        user.setPassword("pass");
        user.setFirstName("A");
        user.setLastName("B");

        // When
        user.onCreate();

        // Then
        assertNotNull(user.getCreatedAt());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        User user = new User();
        String id = "user-1";
        String email = "test@example.com";
        String password = "pass";
        String firstName = "Test";
        String lastName = "User";
        LocalDateTime now = LocalDateTime.now();

        // When
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCreatedAt(now);
        user.setGithubAccessToken("token");
        user.setGithubUsername("githubUser");
        user.setGithubConnected(true);

        // Then
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(now, user.getCreatedAt());
        assertEquals("token", user.getGithubAccessToken());
        assertEquals("githubUser", user.getGithubUsername());
        assertTrue(user.getGithubConnected());
    }
}
