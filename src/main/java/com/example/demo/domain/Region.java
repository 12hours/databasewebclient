package com.example.demo.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "regions")

public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    @Size(max = 2000)
    private String region;

    @OneToMany(mappedBy = "region")
    Set<Child> children = new HashSet<>();
}
