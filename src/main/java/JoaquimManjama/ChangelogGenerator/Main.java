// FOR TESTING PURPOSES
package JoaquimManjama.ChangelogGenerator;

import JoaquimManjama.ChangelogGenerator.Services.GitHubApiService;
import org.springframework.web.client.RestTemplate;

public class Main {

    public static void main(String[] args) {
        GitHubApiService service = new GitHubApiService(new RestTemplate());

        service.getGitHubRepositories("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");
    }
}
