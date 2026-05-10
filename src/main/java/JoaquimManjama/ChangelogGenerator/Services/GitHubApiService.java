package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.*;
import JoaquimManjama.ChangelogGenerator.Enums.GitHubChangeType;
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
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        ResponseEntity<List<GitHubRepository>> response = makeGitHubRequest(url, accessToken, new ParameterizedTypeReference<>() {
        });
        return response.getBody();
    }

    public List<GitHubCommitDTO> getCommits(String accessToken, String owner, String repo) { //, String branch, LocalDateTime since, LocalDateTime until) {
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/commits";//?since=" + since.toString() + "&until=" + until.toString()q;
        ResponseEntity<List<GitHubCommitDTO>> response = makeGitHubRequest(url, accessToken, new ParameterizedTypeReference<>() {
        });

        if (response == null) return null;

        List<GitHubCommitDTO> commits = response.getBody().stream().filter(commit -> !isMergeCommit(commit.commit().message())).toList();
        return commits;
    }

    public List<GitHubPullRequestDTO> getMergedPullRequests(String accessToken, String owner, String repo) {//. LocalDateTime since) {

        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/pulls?state=closed";//?since=" + since.toString();

        ResponseEntity<List<GitHubPullRequestDTO>> response = makeGitHubRequest(url, accessToken, new ParameterizedTypeReference<>() {});

        if (response == null) return null;

        return response.getBody();
    }

    public List<GitHubChangeDTO> getRecentChanges(String accessToken, String owner, String repo) { //, String branch, LocalDateTime since) {

        List<GitHubChangeDTO> changes = new ArrayList<>();

        List<GitHubCommitDTO> commits = getCommits(accessToken, owner, repo);
        List<GitHubPullRequestDTO> pulls = getMergedPullRequests(accessToken, owner, repo);

        changes.addAll(commits.stream().map(this::convertCommitToChangeDTO).toList());
        changes.addAll(pulls.stream().map(this::convertPullRequestToChangeDTO).toList());

        changes.sort(Comparator.comparing(GitHubChangeDTO::date));

        return changes;
    }

    private boolean isMergeCommit(String message) {
        if (message == null) return false;
        return message.startsWith("Merge pull request") || message.startsWith("Merge branch");
    }

    private GitHubChangeDTO convertCommitToChangeDTO(GitHubCommitDTO commit) {
        return new GitHubChangeDTO(
                GitHubChangeType.COMMIT,
                commit.sha(), "",
                commit.commit().message(),
                commit.commit().author().name(),
                commit.commit().author().date(),
                new ArrayList<>(),
                commit.html_url()
                );
    }

    private GitHubChangeDTO convertPullRequestToChangeDTO(GitHubPullRequestDTO pr) {
        return new GitHubChangeDTO(
                GitHubChangeType.PULL_REQUEST,
                pr.id().toString(),
                pr.title(),
                pr.body(),
                "",
                pr.mergedAt(),
                getLabels(pr),
                pr.html_url()
        );
    }

    private List<String> getLabels(GitHubPullRequestDTO pr) {
        List<String> labels = new ArrayList<>();

        for (GitHubLabel label : pr.labels()) {
            labels.add(label.name());
        }

        return labels;
    }
}
