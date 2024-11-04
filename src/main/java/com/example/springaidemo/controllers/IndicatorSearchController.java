package com.example.springaidemo.controllers;

import com.example.springaidemo.entities.IndicatorSearch;
import com.example.springaidemo.services.IndicatorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.transformers.TransformersEmbeddingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/indicatorsearch")
@RequiredArgsConstructor
public class IndicatorSearchController {
    @Autowired
    private TransformersEmbeddingClient embeddingClient;

    private final IndicatorSearchService indicatorSearchService;


    @GetMapping("/all")
    public List<IndicatorSearch> getall(){
        return indicatorSearchService.getAll();
    }

    @GetMapping("/ai")
    public List<Double> getResult() throws Exception {
        List<Double> embeddings = embeddingClient.embed("Hello world");
        return embeddings;
    }

    @GetMapping("/extraction")
    public String extracted(){
        return indicatorSearchService.getembedding();
    }
    @GetMapping("/extractionwithoutweight")
    public String extractedwithoutweight(){
        return indicatorSearchService.getembeddingwithoutweight();
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<Map<String, Object>> search(@PathVariable String text){
        Map<String, Object> messageDetails = new HashMap<>();
        messageDetails.put("message", "Searched.");
        messageDetails.put("data", indicatorSearchService.findByText(text));
        return ResponseEntity.status(HttpStatus.OK).body(messageDetails);
    }

    @GetMapping("/advancedsearch/{text}")
    public ResponseEntity<Map<String, Object>> advancedsearch(@PathVariable List<String> text){
        Map<String, Object> messageDetails = new HashMap<>();
        messageDetails.put("message", "Searched.");
        messageDetails.put("data", indicatorSearchService.advancedfindByText(text));
        return ResponseEntity.status(HttpStatus.OK).body(messageDetails);
    }


}
