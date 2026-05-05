package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.GitHubCommit;
import JoaquimManjama.ChangelogGenerator.DTOs.GitHubRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubApiService {

    private RestTemplate restTemplate;

    public GitHubApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private <T> ResponseEntity<T> makeGitHubRequest(String url, String accessToken, ParameterizedTypeReference<T>responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        try {
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException e ) {
            StringBuilder message = new StringBuilder();
            int errorCode = e.getStatusCode().value();
            message.append("Error: " + errorCode);

            switch (errorCode) {
                case 401:
                    message.append("(invalid token)");
                case 403:
                    message.append("(rate limit exceeded)");
                case 404:
                    message.append("(repository does not exist)");
                default:
                    message.append("someting went wrong");
            }

            message.append("\n" + e.getMessage());
            System.out.println(message);
        }
        return null;
    }

    public List<GitHubRepository> getGitHubRepositories(String accessToken) {

        String url = "https://api.github.com/user/repos?sort=updated&per_page=100";
        ResponseEntity<List<GitHubRepository>> response = makeGitHubRequest(url, accessToken,  new ParameterizedTypeReference<List<GitHubRepository>>() {});

        return response.getBody();
    }

    public List<GitHubCommit> getCommits(String accessToken, String owner, String repo) { //, String branch, LocalDateTime since, LocalDateTime until) {
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/commits";
        ResponseEntity<List<GitHubCommit>> response = makeGitHubRequest(url, accessToken,  new ParameterizedTypeReference<List<GitHubCommit>>() {});

        return response.getBody();
    }
}
