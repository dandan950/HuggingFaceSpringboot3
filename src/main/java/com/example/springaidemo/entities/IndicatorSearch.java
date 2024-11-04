package com.example.springaidemo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("analytics-indicatorSearch")
public class IndicatorSearch {

    @Id
    private String id;
    private String name;
    private String indicatorType;
    private List<String> platforms;
    private List<String> activityTypes;
    private List<String>actionOnActivities;
    private List<String> activities;
    private String vistypename;
    private String indicatorid;
    private String createdbyid;
    private List<Double> embeddings;
    private List<Double> embeddingsWithoutWeight;
    private double score;


    // only for BASIC & MULTI_LEVEL
    private String anamethodname;
    private String anamethoddescription;


}
