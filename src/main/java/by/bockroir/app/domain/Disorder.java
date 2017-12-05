package by.bockroir.app.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "disorders")
public class Disorder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    @Size(max = 200)
    String disorder;

    @ManyToMany(mappedBy = "disorders")
    Set<Survey> surveys = new HashSet<>();

    @PreRemove
    private void removeDisorderFromSurveys(){
        for (Survey s : surveys) {
            s.getDisorders().remove(this);
        }
    }
}
