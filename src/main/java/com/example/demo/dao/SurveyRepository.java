package com.example.demo.dao;

import com.example.demo.domain.Survey;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SurveyRepository extends PagingAndSortingRepository<Survey, Long>{
}
