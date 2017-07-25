package client.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "surveys")
public class Survey implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private int id;

    @Column(name = "survey_date")
    private Date surveyDate;

    @Column(name = "protocol_number")
    private int protocolNumber;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "determined_disorder")
    private String determinedDisorder;

    @Column(name = "recommended_programm")
    private String recommendedProgrammOfEducation;

    @Column(name = "recommended_form")
    private String recommendedFormOfEducation;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    @JoinColumn(name = "child_id")
    @JsonBackReference
    private Child child;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Survey survey = (Survey) o;

        return id == survey.id;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }
}
