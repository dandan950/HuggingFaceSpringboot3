package com.example.springaidemo.repositories;

import com.example.springaidemo.entities.QuestionSearch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionSearchRepository extends MongoRepository<QuestionSearch,String> {
}