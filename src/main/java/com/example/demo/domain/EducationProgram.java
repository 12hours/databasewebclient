package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "education_programs")
public class EducationProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    @Size(max = 2000)
    private String program;

    @ManyToMany(mappedBy = "educationPrograms")
    Set<Survey> surveys = new HashSet<>();
}
