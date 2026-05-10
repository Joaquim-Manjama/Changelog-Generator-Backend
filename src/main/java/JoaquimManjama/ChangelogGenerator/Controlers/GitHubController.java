package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.DTOs.GitHubChangeDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.GitHubCommitDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.GitHubPullRequestDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.GitHubRepository;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.UserRepository;
import JoaquimManjama.ChangelogGenerator.Services.GitHubApiService;
import JoaquimManjama.ChangelogGenerator.Services.GitHubService;
import JoaquimManjama.ChangelogGenerator.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/github")
public class GitHubController {

    private final GitHubService service;
    private final GitHubApiService apiService;
    private final UserService userService;
    private final UserRepository userRepository;

    public GitHubController(GitHubService service, GitHubApiService apiService, UserService userService,  UserRepository userRepository) {
        this.service = service;
        this.apiService = apiService;
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

    @DeleteMapping("/disconnect")
    public ResponseEntity<?> disconect(@AuthenticationPrincipal User user) {
        user.setGithubConnected(false);
        user.setGithubUsername(null);
        user.setGithubAccessToken(null);

        userRepository.save(user);

        return  ResponseEntity.ok(Map.of("message", "Disconnected"));
    }

    @GetMapping("/get/repos")
    public ResponseEntity<?> getRepos(@AuthenticationPrincipal User user) {
        String accessToken = user.getGithubAccessToken();
        List<GitHubRepository> response = apiService.getGitHubRepositories(accessToken);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/commits")
    public ResponseEntity<?> getCommits(@AuthenticationPrincipal User user, @RequestParam String repo) {
        String accessToken = user.getGithubAccessToken();
        String owner = user.getGithubUsername();
        List<GitHubCommitDTO> response = apiService.getCommits(accessToken, owner, repo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/pulls")
    public ResponseEntity<?> getPulls(@AuthenticationPrincipal User user, @RequestParam String repo) {
        String accessToken = user.getGithubAccessToken();
        String owner = user.getGithubUsername();
        List<GitHubPullRequestDTO> response = apiService.getMergedPullRequests(accessToken, owner, repo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/changes")
    public ResponseEntity<?> getChanges(@AuthenticationPrincipal User user, @RequestParam String repo) {
        String accessToken = user.getGithubAccessToken();
        String owner = user.getGithubUsername();
        List<GitHubChangeDTO> response = apiService.getRecentChanges(accessToken, owner, repo);
        return ResponseEntity.ok(response);
    }
}
