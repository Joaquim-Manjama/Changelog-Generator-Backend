package JoaquimManjama.ChangelogGenerator;

import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Create JwtUtil instance
        jwtUtil = new JwtUtil();

        // Set the secret and expiration using reflection
        // (because in real code, these come from application.properties)
        ReflectionTestUtils.setField(jwtUtil, "secret", "MyVerySecretKeyThatIsAtLeast256BitsLongForHS256Algorithm");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 86400000L); // 24 hours

        // Create test user
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test User");
    }

    @Test
    void testGenerateToken() {
        // When: Generate a token
        String token = jwtUtil.generateToken(testUser.getEmail());

        // Then: Token should not be null or empty
        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
        assertTrue(token.split("\\.").length == 3, "JWT should have 3 parts separated by dots");
    }

    @Test
    void testExtractEmail() {
        // Given: A valid token
        String token = jwtUtil.generateToken(testUser.getEmail());

        // When: Extract email from token
        String email = jwtUtil.extractEmail(token);

        // Then: Email should match
        assertEquals(testUser.getEmail(), email, "Extracted email should match original");
    }

    @Test
    void testValidateToken_ValidToken() {
        // Given: A valid token
        String token = jwtUtil.generateToken(testUser.getEmail());

        // When: Validate the token
        boolean isValid = jwtUtil.validateToken(token, testUser.getEmail());

        // Then: Token should be valid
        assertTrue(isValid, "Token should be valid");
    }

    @Test
    void testValidateToken_WrongUser() {
        // Given: Token for one user
        String token = jwtUtil.generateToken("user1@example.com");

        // When: Validate with different user
        User differentUser = new User();
        differentUser.setEmail("user2@example.com");
        boolean isValid = jwtUtil.validateToken(token, differentUser.getEmail());

        // Then: Token should be invalid
        assertFalse(isValid, "Token should be invalid for different user");
    }

    @Test
    void testValidateToken_ExpiredToken() {
        // Given: Create JwtUtil with very short expiration (1 millisecond)
        JwtUtil shortExpirationJwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(shortExpirationJwtUtil, "secret", "MyVerySecretKeyThatIsAtLeast256BitsLongForHS256Algorithm");
        ReflectionTestUtils.setField(shortExpirationJwtUtil, "expiration", 1L); // 1 millisecond

        // When: Generate token and wait for it to expire
        String token = shortExpirationJwtUtil.generateToken(testUser.getEmail());

        try {
            Thread.sleep(10); // Wait 10ms to ensure expiration
        } catch (InterruptedException e) {
            fail("Thread sleep interrupted");
        }

        // Then: Token should be expired/invalid
        assertThrows(Exception.class, () -> {
            shortExpirationJwtUtil.validateToken(token, testUser.getEmail());
        }, "Expired token should throw exception");
    }

    @Test
    void testExtractEmail_InvalidToken() {
        // Given: An invalid token
        String invalidToken = "invalid.token.here";

        // When/Then: Should throw exception
        assertThrows(Exception.class, () -> {
            jwtUtil.extractEmail(invalidToken);
        }, "Invalid token should throw exception");
    }

    @Test
    void testExtractEmail_TamperedToken() {
        // Given: A valid token that we'll tamper with
        String validToken = jwtUtil.generateToken(testUser.getEmail());
        String tamperedToken = validToken.substring(0, validToken.length() - 5) + "AAAAA";

        // When/Then: Tampered token should throw exception
        assertThrows(Exception.class, () -> {
            jwtUtil.extractEmail(tamperedToken);
        }, "Tampered token should throw exception");
    }
}
