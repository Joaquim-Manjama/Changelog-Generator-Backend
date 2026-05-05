package JoaquimManjama.ChangelogGenerator;

import JoaquimManjama.ChangelogGenerator.Services.GitHubApiService;
import org.springframework.web.client.RestTemplate;

public class Main {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        GitHubApiService service = new GitHubApiService(restTemplate);
        service.getGitHubRepositories("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");
    }
}
