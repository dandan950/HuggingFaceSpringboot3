package com.example.springaidemo.services;

import com.example.springaidemo.entities.IndicatorSearch;
import com.example.springaidemo.entities.QuestionSearch;
import com.example.springaidemo.repositories.IndicatorSearchRepository;
import com.example.springaidemo.repositories.QuestionSearchRepository;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.ai.transformers.TransformersEmbeddingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QuestionSearchService {
    private final QuestionSearchRepository questionSearchRepository;
    private final IndicatorSearchRepository indicatorSearchRepository;
    @Autowired
    private TransformersEmbeddingClient embeddingClient;

    @Autowired
    @Qualifier("primaryMongoTemplate")
    private final MongoTemplate primaryMongoTemplate;
    @Autowired
    MongoConverter converter;

    @PostConstruct
    public void init() {
        collection = primaryMongoTemplate.getCollection("analytics-questionSearch");
    }
    private MongoCollection<Document> collection;


    public List <Double> getembeddings(String text){
        return embeddingClient.embed(text);
    }

    public String getembedding (){
        List<QuestionSearch> questionSearchList=questionSearchRepository.findAll();
        Iterator<QuestionSearch> iterator = questionSearchList.iterator();

        while (iterator.hasNext()){
            QuestionSearch  questionSearch=iterator.next();
            String name=questionSearch.getName();
            System.out.println(name);
            String goalname=questionSearch.getGoalname();
            int count=questionSearch.getCount();
            List <String> indicatoridlist=questionSearch.getIndicatorids();
            List <List<Double>> embeddingslist=new ArrayList<>();
            List <Double> embeddings =new ArrayList<>();
            for  (int i=0;i<count;i++){
                String id =indicatoridlist.get(i);
                System.out.println(id);
                ObjectId objectId = new ObjectId(id);
                IndicatorSearch indicatorSearch=indicatorSearchRepository.findByIndicatorId(id);
                embeddingslist.add(indicatorSearch.getEmbeddings());
            }
            List<Double> nameembedding=getembeddings(name);
            List<Double> goalembedding=getembeddings(goalname);

            for (int i=0;i<384;i++){
                Double value= nameembedding.get(i)+goalembedding.get(i);
                for(int j=0;j<count;j++){
                    value+=embeddingslist.get(j).get(i);
                }
                int num=count+2;
                embeddings.add(value/num);
            }
            questionSearch.setEmbeddings(embeddings);
            questionSearchRepository.save(questionSearch);

        }

        return "extracted";
    }

    public  List<QuestionSearch> findbyText(String text){
        List<QuestionSearch> questionSearches=new ArrayList<>();
        String Pretext="query: "+text;
        List<Double> em = getembeddings(Pretext);

        // Define the aggregation pipeline
        List<Document> pipeline = Arrays.asList(
                new Document("$vectorSearch", new Document()
                        .append("index", "QuestionSearch_index")
                        .append("path", "embeddings")
                        .append("queryVector", em)
                        .append("numCandidates", 50L)
                        .append("limit", 5L)
                        .append("filter", new Document())),
                new Document("$project", new Document()
                        .append("_id", 1)
                        .append("name", 1)
                        .append("goalname", 1)
                        .append("count",1)
                        .append("indicatorids",1)
                        .append("score", new Document("$meta", "vectorSearchScore")))
        );

        AggregateIterable<Document> result = collection.aggregate(pipeline);
        for (Document doc : result) {
            Double score = doc.getDouble("score");
            String formattedScore = String.format("%.4f", score);
            double roundedScore = Double.parseDouble(formattedScore);
            QuestionSearch questionSearch = converter.read(QuestionSearch.class, doc);
            questionSearch.setScore(roundedScore);
            questionSearches.add(questionSearch);
        }

        return questionSearches;
    }

    public  List<QuestionSearch> advancedfindByText(List<String> text){
        List<QuestionSearch> questionSearches=new ArrayList<>();
        String concatenatedText = String.join(",", text);
        String Pretext="query: "+concatenatedText;
        List<Double> em = getembeddings(Pretext);
        // Define the aggregation pipeline
        List<Document> pipeline = Arrays.asList(
                new Document("$vectorSearch", new Document()
                        .append("index", "QuestionSearch_index")
                        .append("path", "embeddings")
                        .append("queryVector", em)
                        .append("numCandidates", 50L)
                        .append("limit", 10L)
                        .append("filter", new Document())),
                new Document("$project", new Document()
                        .append("_id", 1)
                        .append("name", 1)
                        .append("goalname", 1)
                        .append("count",1)
                        .append("indicatorids",1)
                        .append("score", new Document("$meta", "vectorSearchScore")))
        );

        AggregateIterable<Document> result = collection.aggregate(pipeline);
        for (Document doc : result) {
            Double score = doc.getDouble("score");
            String formattedScore = String.format("%.4f", score);
            if (score > 0.6) {
                double roundedScore = Double.parseDouble(formattedScore);
                QuestionSearch questionSearch = converter.read(QuestionSearch.class, doc);
                questionSearch.setScore(roundedScore);
                questionSearches.add(questionSearch);
            }

        }
        return questionSearches;
    }


}
