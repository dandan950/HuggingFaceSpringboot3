package com.example.springaidemo.repositories;

import com.example.springaidemo.entities.IndicatorSearch;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IndicatorSearchRepository extends MongoRepository<IndicatorSearch,String> {

    @Query("{ 'indicatorid': ?0 }")
    IndicatorSearch findByIndicatorId(String indicatorId);


}