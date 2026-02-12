package JoaquimManjama.ChangelogGenerator;

import JoaquimManjama.ChangelogGenerator.Security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChangelogGeneratorApplicationTests {

    @Autowired
    private JwtUtil jwtUtil;

	@Test
	void contextLoads() {
    }

    @Test
    void jwtIsWorking() {
        /// 1. Same email is valid
        System.out.println("\n  ****  Same email Test!  ****");
        String token1 = jwtUtil.generateToken("test1@example.com");
        System.out.println("Token: " + token1);

        String email1 = jwtUtil.extractEmail(token1);
        System.out.println("Email: " + email1);

        boolean valid1 = jwtUtil.validateToken(token1, "test1@example.com");
        System.out.println("Valid: " + valid1);
        assertTrue(valid1);

        ///  2. Different emails are not valid
        System.out.println("\n  ****  Different email Test!  ****");
        String token2 = jwtUtil.generateToken("test2@example.com");
        System.out.println("Token: " + token2);

        String email2 = jwtUtil.extractEmail(token2);
        System.out.println("Email: " + email2);

        boolean valid2 = jwtUtil.validateToken(token2, "test@example.com");
        System.out.println("Valid: " + valid2);
        assertFalse(valid2);
    }
}
