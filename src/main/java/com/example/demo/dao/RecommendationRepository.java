package com.example.demo.dao;

import com.example.demo.domain.Recommendation;
import org.springframework.data.repository.CrudRepository;

public interface RecommendationRepository extends CrudRepository<Recommendation, Long>{
}
