package JoaquimManjama.ChangelogGenerator.Entities;

import JoaquimManjama.ChangelogGenerator.Enums.EntryCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="changelog_entries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangelogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "release_id", nullable = false)
    private Release release;

    @Enumerated(EnumType.STRING)
    private EntryCategory category;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer displayOrder;
}
