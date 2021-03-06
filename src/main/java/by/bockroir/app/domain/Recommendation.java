package by.bockroir.app.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    @Size(max = 2000)
    private String recommendation;

    @ManyToMany(mappedBy = "recommendations")
    Set<Survey> surveys = new HashSet<>();

    @PreRemove
    private void removeRecommendationFromSurveys() {
        for (Survey s : surveys) {
            s.getRecommendations().remove(this);
        }
    }
}
