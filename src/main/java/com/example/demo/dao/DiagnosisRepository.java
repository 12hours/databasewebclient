package com.example.demo.dao;

import com.example.demo.domain.Diagnosis;
import org.springframework.data.repository.CrudRepository;

public interface DiagnosisRepository extends CrudRepository<Diagnosis, Long> {
}
