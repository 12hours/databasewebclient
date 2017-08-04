package com.example.demo.dao;

import com.example.demo.domain.Disorder;
import org.springframework.data.repository.CrudRepository;

public interface DisorderRepository extends CrudRepository<Disorder, Long>{
}
