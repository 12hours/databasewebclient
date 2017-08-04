package core.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "edu_programs")
public class EducationProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(unique = true)
    @Size(max = 2000)
    private String program;

    @ManyToMany(mappedBy = "eduPrograms")
    @JsonFilter("OnlyIdFilter")
    Set<Survey> surveys = new HashSet<>();
}