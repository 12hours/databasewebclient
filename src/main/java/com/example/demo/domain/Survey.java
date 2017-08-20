package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "surveys",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"protocol_number", "survey_date"})
)
public class Survey implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @Column(name = "survey_date")
    @Temporal(TemporalType.DATE)
    private Date surveyDate;

    @Column(name = "protocol_number")
    private String protocolNumber;

    @Column(name = "child_name")
    private String childName;
    
    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "determined_disorder")
    private String determinedDisorder;

    @Column(name = "recommended_program")
    @Size(max = 2000)
    private String recommendedProgramOfEducation;

    @Column(name = "recommended_form")
    @Size(max = 2000)
    private String recommendedFormOfEducation;

    @Column(name = "remarks")
    @Size(max = 2000)
    private String remarks;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "child_id", referencedColumnName = "child_id", unique = false)
    private Child child;

    @ManyToMany
    @JoinTable(name = "surveys_recommendations",
        joinColumns = @JoinColumn(name = "survey_id"),
        inverseJoinColumns = @JoinColumn(name="rec_id"))
    private Set<Recommendation> recommendations = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "surveys_edu_programs",
        joinColumns = @JoinColumn(name = "survey_id"),
        inverseJoinColumns = @JoinColumn(name = "edu_pr_id"))
    private Set<EducationProgram> educationPrograms = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "surveys_diagnoses",
        joinColumns = @JoinColumn(name = "survey_id"),
        inverseJoinColumns = @JoinColumn(name = "diagnosis_id"))
    private Set<Diagnosis> diagnoses = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "surveys_disorders",
        joinColumns = @JoinColumn(name = "survey_id"),
        inverseJoinColumns = @JoinColumn(name = "disorder_id"))
    private Set<Disorder> disorders = new HashSet<>();

    @PrePersist
    @PreUpdate
//    @PostLoad
    private void setChildName(){
        this.childName = child.getFamilyName() + " "
                + child.getName() + " "
                + child.getPatrName();
    }

    @Override
    public boolean equals(Object o) {
        if (id == null) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Survey survey = (Survey) o;

        return id == survey.id;
    }

    @Override
    public int hashCode() {
        // TODO: looking bad
        if (id == null) return super.hashCode();
        long result = super.hashCode();
        result = 31 * result + id;
        return (int) result;
    }
}
