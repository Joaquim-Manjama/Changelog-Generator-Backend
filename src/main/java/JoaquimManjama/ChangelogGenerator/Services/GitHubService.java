package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.Config.GitHubConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class GitHubService {

    private final GitHubConfig gitHubConfig;

    private final RestTemplate restTemplate;

    public GitHubService(GitHubConfig gitHubConfig,  RestTemplate restTemplate) {
        this.gitHubConfig = gitHubConfig;
        this.restTemplate = restTemplate;
    }

    // Generate the URL to redirect user to GitHub
    public String getAuthorizationUrl(String userId) {

        String state = userId;

        return "https://github.com/login/oauth/authorize"
                + "?client_id=" + gitHubConfig.getClientId()
                + "&redirect_uri=" + gitHubConfig.getRedirectUri()
                + "&scope=repo,read:user"
                + "&state=" + state;
    }

    // Exchange code for access token
    public String getAccessToken(String code) {
        String tokenUrl = "https://github.com/login/oauth/access_token";

        // Prepare request body
        Map<String, String> params = new HashMap<>();
        params.put("client_id", gitHubConfig.getClientId());
        params.put("client_secret", gitHubConfig.getClientSecret());
        params.put("code", code);
        params.put("redirect_uri", gitHubConfig.getRedirectUri());

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);

        // Make POST request
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        // Extract access token from response
        Map<String, Object> body = response.getBody();
        return (String) body.get("access_token");
    }

    // Get user's Github username
    public String getGitHubUsername(String accessToken) {
        String userUrl = "https://api.github.com/user";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                userUrl,
                HttpMethod.GET,
                request,
                Map.class
        );

        Map<String, Object> body = response.getBody();

        return (String) body.get("login");
    }
}
