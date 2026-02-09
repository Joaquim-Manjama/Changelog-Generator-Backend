package JoaquimManjama.ChangelogGenerator.DTOs;

import JoaquimManjama.ChangelogGenerator.Entities.Project;

import java.time.LocalDateTime;

public class ProjectDTO {

    private long id;
    private String name;
    private String slug;
    private String githubRepo;
    private long userId;
    private LocalDateTime createdAt;

    public ProjectDTO(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.slug = project.getSlug();
        this.githubRepo = project.getGithubRepo();
        this.userId = project.getUser().getId();
        this.createdAt = project.getCreatedAt();
    }
}