package JoaquimManjama.ChangelogGenerator.Entities;

import JoaquimManjama.ChangelogGenerator.Enums.ReleaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="releases")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="project_id",  nullable=false)
    private Project project;

    @Column(nullable = false, length = 50)
    private String version;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private ReleaseStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChangelogEntry> changelogEntries = new ArrayList<>();
}
