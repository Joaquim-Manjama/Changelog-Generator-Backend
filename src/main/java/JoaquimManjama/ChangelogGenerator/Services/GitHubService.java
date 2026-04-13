package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.Config.GitHubConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitHubService {

    private final GitHubConfig gitHubConfig;

    public GitHubService(GitHubConfig gitHubConfig) {
        this.gitHubConfig = gitHubConfig;
    }

    // Generate the URL to redirect user to GitHub
    public String getAuthorizationUrl() {

    }

    // Exchange code for access token
    public String getAccessToken(String code) {

    }

    // Get user's Github username
    public String getGitHubUsername(String accessToken) {

    }
}
