package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.UserRepository;
import JoaquimManjama.ChangelogGenerator.Services.GitHubService;
import JoaquimManjama.ChangelogGenerator.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/github")
public class GitHubController {

    private final GitHubService service;
    private final UserService userService;
    private final UserRepository userRepository;

    public GitHubController(GitHubService service, UserService userService,  UserRepository userRepository) {
        this.service = service;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/authorize")
    public ResponseEntity<?> authorize(@AuthenticationPrincipal User user) {
        String authUrl = service.getAuthorizationUrl(user.getId());
        return ResponseEntity.ok(Map.of("url", authUrl));
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam String code, @RequestParam String state) {

        User user = userRepository.findById(state).get();

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
                .header("Location", "http://localhost:5173/dashboard?github=connected")
                .build();
    }

    @GetMapping("/status")
    public ResponseEntity<?> status(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(Map.of("connected", user.getGithubConnected(), "username", user.getGithubUsername()));
    }

    @DeleteMapping("/disconect")
    public ResponseEntity<?> disconect(@AuthenticationPrincipal User user) {
        user.setGithubConnected(false);
        user.setGithubUsername(null);
        user.setGithubAccessToken(null);

        userRepository.save(user);

        return  ResponseEntity.ok(Map.of("message", "Disconnected"));
    }
}
