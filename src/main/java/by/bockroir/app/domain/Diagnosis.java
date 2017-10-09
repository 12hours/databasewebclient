package by.bockroir.app.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "diagnoses")
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    @Size(max = 200)
    String diagnosis;

    //    @JsonIgnoreProperties(value = {"surveyDate", "protocolNumber", "diagnosis", "determinedDisorder",
    //            "recommendedProgramOfEducation", "recommendedFormOfEducation", "remarks", "child", "recommends",
    //            "eduPrograms", "diagnoses", "disorders"})
    @ManyToMany(mappedBy = "diagnoses")
    Set<Survey> surveys = new HashSet<>();

}
