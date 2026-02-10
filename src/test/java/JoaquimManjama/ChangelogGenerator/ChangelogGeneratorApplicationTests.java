package JoaquimManjama.ChangelogGenerator;

import JoaquimManjama.ChangelogGenerator.Security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class ChangelogGeneratorApplicationTests {

    @Autowired
    private JwtUtil jwtUtil;

	@Test
	void contextLoads() {
        String token = jwtUtil.generateToken("test@example.com");
        System.out.println("Token: " + token);

        String email = jwtUtil.extractEmail(token);
        System.out.println("Email: " + email);

        boolean valid = jwtUtil.validateToken(token, "test1@example.com");
        System.out.println("Valid: " + valid);
    }
}
