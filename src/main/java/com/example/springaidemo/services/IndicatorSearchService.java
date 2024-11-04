package com.example.springaidemo.services;

import com.example.springaidemo.entities.IndicatorSearch;
import com.example.springaidemo.repositories.IndicatorSearchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
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
public class IndicatorSearchService {

    @Autowired
    @Qualifier("primaryMongoTemplate")
    private final MongoTemplate primaryMongoTemplate;
    @Autowired
    MongoConverter converter;
    ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        collection = primaryMongoTemplate.getCollection("analytics-indicatorSearch");
    }
    private MongoCollection<Document> collection;

    @Autowired
    private TransformersEmbeddingClient embeddingClient;

    private final IndicatorSearchRepository indicatorSearchRepository;

    public List<IndicatorSearch> getAll(){
        return indicatorSearchRepository.findAll();
    }

    public List <Double> getembeddings(String text){
        return embeddingClient.embed(text);
    }

    public String  getembeddingwithoutweight () {
        List<IndicatorSearch> indicatorSearches = getAll();
        Iterator<IndicatorSearch> iterator = indicatorSearches.iterator();
        while (iterator.hasNext()) {
            List<Double> embeddingswithoutweight = new ArrayList<>();
            IndicatorSearch indicatorSearch = iterator.next();
            String type = indicatorSearch.getIndicatorType();
            List<String> platformslist = indicatorSearch.getPlatforms();
            List<String> activityTypelist = indicatorSearch.getActivityTypes();
            List<String> actionOnActivitieslist = indicatorSearch.getActionOnActivities();
            List<String> activitieslist = indicatorSearch.getActivities();
            String name = indicatorSearch.getName();
            String vistype = indicatorSearch.getVistypename();
            String text="";
            if (type.equals("BASIC")) {
                //weight
//                indicatorSearchRepository.delete(indicatorSearch);
                String anamethodname = indicatorSearch.getAnamethodname();
                String anamethoddescription = indicatorSearch.getAnamethoddescription();

                //combine them into text
                ObjectNode combined = objectMapper.createObjectNode();
                combined.put("name", name);
                combined.put("anamethodname",anamethodname);
                combined.put("anamethoddescription",anamethoddescription);
                combined.put("vis",vistype);
                //platformslist
                ArrayNode platformslistArray=objectMapper.createArrayNode();
                for(String i:platformslist) {
                    platformslistArray.add(i);
                }
                combined.put("platformslist",platformslistArray);
                //activityTypelist
                ArrayNode activityTypelistArray=objectMapper.createArrayNode();
                for(String i:activityTypelist) {
                    activityTypelistArray.add(i);
                }
                combined.put("activityTypelist",activityTypelistArray);
                //actionOnActivitieslist
                ArrayNode actionOnActivitieslistArray=objectMapper.createArrayNode();
                for(String i:actionOnActivitieslist) {
                    actionOnActivitieslistArray.add(i);
                }
                combined.put("actionOnActivitieslist",actionOnActivitieslistArray);
                //activitieslist
                ArrayNode activitieslistArray=objectMapper.createArrayNode();
                for(String i:activitieslist) {
                    activitieslistArray.add(i);
                }
                combined.put("activitieslist",activitieslistArray);
                String input = combined.toString();
                String regex = "[\\p{Punct}&&[^:\\,]]";
                text = input.replaceAll(regex, " ");
                embeddingswithoutweight=getembeddings(text);
                indicatorSearch.setEmbeddingsWithoutWeight(embeddingswithoutweight);
                indicatorSearchRepository.save(indicatorSearch);


            }

            if (type.equals("COMPOSITE")) {
                //combine them into text
                ObjectNode combined = objectMapper.createObjectNode();
                combined.put("name", name);
                combined.put("vis",vistype);
                //platformslist
                ArrayNode platformslistArray=objectMapper.createArrayNode();
                for(String i:platformslist) {
                    platformslistArray.add(i);
                }
                combined.put("platformslist",platformslistArray);
                //activityTypelist
                ArrayNode activityTypelistArray=objectMapper.createArrayNode();
                for(String i:activityTypelist) {
                    activityTypelistArray.add(i);
                }
                combined.put("activityTypelist",activityTypelistArray);
                //actionOnActivitieslist
                ArrayNode actionOnActivitieslistArray=objectMapper.createArrayNode();
                for(String i:actionOnActivitieslist) {
                    actionOnActivitieslistArray.add(i);
                }
                combined.put("actionOnActivitieslist",actionOnActivitieslistArray);
                //activitieslist
                ArrayNode activitieslistArray=objectMapper.createArrayNode();
                for(String i:activitieslist) {
                    activitieslistArray.add(i);
                }
                combined.put("activitieslist",activitieslistArray);
                String input = combined.toString();
                String regex = "[\\p{Punct}&&[^:\\,]]";
                text = input.replaceAll(regex, " ");
                embeddingswithoutweight=getembeddings(text);
                indicatorSearch.setEmbeddingsWithoutWeight(embeddingswithoutweight);
                indicatorSearchRepository.save(indicatorSearch);
            }
            if (type.equals("MULTI_LEVEL")) {
                String anamethodname = indicatorSearch.getAnamethodname();
                String anamethoddescription = indicatorSearch.getAnamethoddescription();

                //combine them into text
                ObjectNode combined = objectMapper.createObjectNode();
                combined.put("name", name);
                combined.put("anamethodname",anamethodname);
                combined.put("anamethoddescription",anamethoddescription);
                combined.put("vis",vistype);
                //platformslist
                ArrayNode platformslistArray=objectMapper.createArrayNode();
                for(String i:platformslist) {
                    platformslistArray.add(i);
                }
                combined.put("platformslist",platformslistArray);
                //activityTypelist
                ArrayNode activityTypelistArray=objectMapper.createArrayNode();
                for(String i:activityTypelist) {
                    activityTypelistArray.add(i);
                }
                combined.put("activityTypelist",activityTypelistArray);
                //actionOnActivitieslist
                ArrayNode actionOnActivitieslistArray=objectMapper.createArrayNode();
                for(String i:actionOnActivitieslist) {
                    actionOnActivitieslistArray.add(i);
                }
                combined.put("actionOnActivitieslist",actionOnActivitieslistArray);
                //activitieslist
                ArrayNode activitieslistArray=objectMapper.createArrayNode();
                for(String i:activitieslist) {
                    activitieslistArray.add(i);
                }
                combined.put("activitieslist",activitieslistArray);
                String input = combined.toString();
                String regex = "[\\p{Punct}&&[^:\\,]]";
                text = input.replaceAll(regex, " ");
                embeddingswithoutweight=getembeddings(text);

                indicatorSearch.setEmbeddingsWithoutWeight(embeddingswithoutweight);
                indicatorSearchRepository.save(indicatorSearch);
            }

        }
        return "String";
    }


    public String  getembedding (){
        List<IndicatorSearch> indicatorSearches=getAll();
        Iterator<IndicatorSearch> iterator = indicatorSearches.iterator();
        while(iterator.hasNext()){
            List <Double> embeddings=new ArrayList<>();
            IndicatorSearch indicatorSearch=iterator.next();
            String type=indicatorSearch.getIndicatorType();
            List<String> platformslist=indicatorSearch.getPlatforms();
            List<String> activityTypelist=indicatorSearch.getActivityTypes();
            List<String> actionOnActivitieslist =indicatorSearch.getActionOnActivities();
            List<String> activitieslist=indicatorSearch.getActivities();
            String name=indicatorSearch.getName();
            String vistype=indicatorSearch.getVistypename();
            if (type.equals("BASIC")){
                               //weight
//                indicatorSearchRepository.delete(indicatorSearch);
                String anamethodname=indicatorSearch.getAnamethodname();
                String anamethoddescription=indicatorSearch.getAnamethoddescription();

               List <Double> actionOnActivitiesembeddings=getembeddings(String.join(" ", actionOnActivitieslist));
               List <Double> activitiestypesembeddings=getembeddings(String.join(" ", activityTypelist));
               List <Double> nameembeddings=getembeddings(name);
               List <Double> anathednameembeddings=getembeddings(anamethodname);
               List <Double> anatheddescriptionembeddings=getembeddings(anamethoddescription);
               List <Double> vistypeembeddings=getembeddings(vistype);
               List <Double> platformembeddings=getembeddings(String.join(" ", platformslist));
               List <Double> activitiesembeddings=getembeddings(String.join(" ", activitieslist));

               for (int i=0;i<384;i++){
                   double value=4*nameembeddings.get(i)+4* actionOnActivitiesembeddings.get(i)+4*activitiestypesembeddings.get(i)
                           +3*anathednameembeddings.get(i)+3*anatheddescriptionembeddings.get(i)
                           +2*vistypeembeddings.get(i)
                           +activitiesembeddings.get(i)+ platformembeddings.get(i);
                   double avergagevalue=value/22;
                   embeddings.add(avergagevalue);
               }
                indicatorSearch.setEmbeddings(embeddings);
               indicatorSearchRepository.save(indicatorSearch);


            }

            if(type.equals("COMPOSITE")){

                               //weight
               List <Double> actionOnActivitiesembeddings=getembeddings(String.join(" ", actionOnActivitieslist));
               List <Double> activitiestypesembeddings=getembeddings(String.join(" ", activityTypelist));
               List <Double> nameembeddings=getembeddings(name);
               List <Double> vistypeembeddings=getembeddings(vistype);
               List <Double> platformembeddings=getembeddings(String.join(" ", platformslist));
               List <Double> activitiesembeddings=getembeddings(String.join(" ", activitieslist));


               for (int i=0;i<384;i++){
                   double value=4*nameembeddings.get(i)+4* actionOnActivitiesembeddings.get(i)+4*activitiestypesembeddings.get(i)
                           +2*vistypeembeddings.get(i)
                           +activitiesembeddings.get(i)+ platformembeddings.get(i);
                   double avergagevalue=value/16;
                   embeddings.add(avergagevalue);
               }
                indicatorSearch.setEmbeddings(embeddings);
                indicatorSearchRepository.save(indicatorSearch);
            }
            if (type.equals("MULTI_LEVEL")){
                String anamethodname=indicatorSearch.getAnamethodname();
                String anamethoddescription=indicatorSearch.getAnamethoddescription();
                               //weight
               List <Double> actionOnActivitiesembeddings=getembeddings(String.join(" ", actionOnActivitieslist));
               List <Double> activitiestypesembeddings=getembeddings(String.join(" ", activityTypelist));
               List <Double> nameembeddings=getembeddings(name);
               List <Double> anathednameembeddings=getembeddings(anamethodname);
               List <Double> anatheddescriptionembeddings=getembeddings(anamethoddescription);
               List <Double> vistypeembeddings=getembeddings(vistype);
               List <Double> platformembeddings=getembeddings(String.join(" ", platformslist));
               List <Double> activitiesembeddings=getembeddings(String.join(" ", activitieslist));


               for (int i=0;i<384;i++){
                   double value=4*nameembeddings.get(i)+4* actionOnActivitiesembeddings.get(i)+4*activitiestypesembeddings.get(i)
                           +3*anathednameembeddings.get(i)+3*anatheddescriptionembeddings.get(i)
                           +2*vistypeembeddings.get(i)
                           +activitiesembeddings.get(i)+ platformembeddings.get(i);
                   double avergagevalue=value/22;
                   embeddings.add(avergagevalue);
               }
            }

            indicatorSearch.setEmbeddings(embeddings);
            indicatorSearchRepository.save(indicatorSearch);
        }

        return "String";

    }




    public List<IndicatorSearch> findByText(String text){
        List<IndicatorSearch> indicatorSearches=new ArrayList<>();
        String Pretext="query: "+text;
        List<Double> em = getembeddings(Pretext);

        // Define the aggregation pipeline
        List<Document> pipeline = Arrays.asList(
                new Document("$vectorSearch", new Document()
                        .append("index", "SemanticSearch_index")
                        .append("path", "embeddings")
                        .append("queryVector", em)
                        .append("numCandidates", 50L)
                        .append("limit", 5L)
                        .append("filter", new Document())),
                new Document("$project", new Document()
                        .append("_id", 1)
                        .append("name", 1)
                        .append("indicatorType", 1)
                        .append("platforms", 1)
                        .append("activityTypes", 1)
                        .append("actionOnActivities", 1)
                        .append("activities", 1)
                        .append("vistypename", 1)
                        .append("anamethodname", 1)
                        .append("anamethoddescription",1)
                        .append("score", new Document("$meta", "vectorSearchScore"))
                        .append("indicatorid",1)
                        .append("createdbyid",1)

                )
        );
        AggregateIterable<Document> result = collection.aggregate(pipeline);
        for (Document doc : result) {
            Double score = doc.getDouble("score");
            String formattedScore = String.format("%.4f", score);
            double roundedScore = Double.parseDouble(formattedScore);
            IndicatorSearch indicatorSearch = converter.read(IndicatorSearch.class, doc);
            indicatorSearch.setScore(roundedScore);
            indicatorSearches.add(indicatorSearch);

        }

        return  indicatorSearches;
    }

    public List<IndicatorSearch> advancedfindByText(List<String> text){
        String concatenatedText = String.join(",", text);
        List<IndicatorSearch> indicatorSearches = new ArrayList<>();
        String Pretext="query: "+concatenatedText;
        List<Double> em = getembeddings(Pretext);
        // Define the aggregation pipeline
        List<Document> pipeline = Arrays.asList(
                new Document("$vectorSearch", new Document()
                        .append("index", "SemanticSearch_index")
                        .append("path", "embeddings")
                        .append("queryVector", em)
                        .append("numCandidates", 50L)
                        .append("limit", 5L)
                        .append("filter", new Document())),
                new Document("$project", new Document()
                        .append("_id", 1)
                        .append("name", 1)
                        .append("indicatorType", 1)
                        .append("platforms", 1)
                        .append("activityTypes", 1)
                        .append("actionOnActivities", 1)
                        .append("activities", 1)
                        .append("vistypename", 1)
                        .append("anamethodname", 1)
                        .append("anamethoddescription",1)
                        .append("score", new Document("$meta", "vectorSearchScore"))
                        .append("indicatorid",1)
                        .append("createdbyid",1)
                )
        );
        AggregateIterable<Document> result = collection.aggregate(pipeline);
        for (Document doc : result) {
            Double score = doc.getDouble("score");
            String formattedScore = String.format("%.4f", score);
            double roundedScore = Double.parseDouble(formattedScore);
            IndicatorSearch indicatorSearch = converter.read(IndicatorSearch.class, doc);
            indicatorSearch.setScore(roundedScore);
            indicatorSearches.add(indicatorSearch);
        }

        return indicatorSearches;

    }







}
