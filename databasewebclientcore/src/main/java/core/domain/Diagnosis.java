package core.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "diagnoses")
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(unique = true)
    @Size(max = 200)
    String diagnosis;

    //    @JsonIgnoreProperties(value = {"surveyDate", "protocolNumber", "diagnosis", "determinedDisorder",
    //            "recommendedProgramOfEducation", "recommendedFormOfEducation", "remarks", "child", "recommends",
    //            "eduPrograms", "diagnoses", "disorders"})
    @ManyToMany(mappedBy = "diagnoses")
    @JsonFilter("OnlyIdFilter")
    Set<Survey> surveys = new HashSet<>();

}
