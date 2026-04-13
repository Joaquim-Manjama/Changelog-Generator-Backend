package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Services.GitHubService;
import JoaquimManjama.ChangelogGenerator.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/github")
public class GitHubController {

    private final GitHubService service;
    private final UserService userService;

    public GitHubController(GitHubService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/authorize")
    public ResponseEntity<?> authorize() {
        String authUrl = service.getAuthorizationUrl();
        return ResponseEntity.ok(Map.of("url", authUrl));
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam String code, @AuthenticationPrincipal User user) {

        // Get access token
        String accessToken = service.getAccessToken(code);

        // Get gitHub username
        String githubUsername = service.getGitHubUsername(accessToken);

        // Update user in database
        user.setGithubAccessToken(accessToken);
        user.setGithubUsername(githubUsername);
        user.setGithubConnected(true);
        userService.addUser(user);

        // Redirect to frontend
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "http://localhost:5173/dashboard")
                .build();
    }
}
