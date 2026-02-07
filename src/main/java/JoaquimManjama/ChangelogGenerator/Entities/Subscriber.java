package JoaquimManjama.ChangelogGenerator.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="subscribers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id",  nullable = false)
    private Project project;

    @Column(nullable=false, length = 255)
    private Long email;

    @Column(nullable=false)
    private Boolean confirmed;

    @Column(nullable=false)
    private LocalDateTime createdAt;
}
