package com.example.demo.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "children")
public class Child implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id")
    private Long id;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "name")
    private String name;

    @Column(name = "patr_name")
    private String patrName;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date birthDate;

    @Column(name = "place_of_education")
    private String placeOfEducation;

    @Column(name = "address")
    private String address;

    @Column(name = "disability")
    private boolean disability;

    @Column(name = "special_needs")
    private boolean specialFacilitiesNeeding;

    @OneToMany(mappedBy = "child", fetch = FetchType.EAGER)
    private Set<Survey> surveys = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (id == null) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Child child = (Child) o;

        return id == child.id;
    }

    @Override
    public int hashCode() {
        if (id == null) return super.hashCode();
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}

