package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "disorders")
public class Disorder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(unique = true)
    @Size(max = 200)
    String disorder;

    @ManyToMany(mappedBy = "disorders")
    Set<Survey> surveys = new HashSet<>();
}