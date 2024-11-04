package com.example.springaidemo.controllers;

import com.example.springaidemo.services.QuestionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questionsearch")
@RequiredArgsConstructor
public class QuestionSearchController {

    private final QuestionSearchService questionSearchService;

    @GetMapping("/extraction")
    public String extracted(){
        return questionSearchService.getembedding();
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<Map<String, Object>> search(@PathVariable String text){
        Map<String, Object> messageDetails = new HashMap<>();
        messageDetails.put("message", "Searched.");
        messageDetails.put("data", questionSearchService.findbyText(text));
        return ResponseEntity.status(HttpStatus.OK).body(messageDetails);
    }

    @GetMapping("/advancedsearch/{text}")
    public ResponseEntity<Map<String, Object>> advancedsearch(@PathVariable List<String> text){
        Map<String, Object> messageDetails = new HashMap<>();
        messageDetails.put("message", "Searched.");
        messageDetails.put("data", questionSearchService.advancedfindByText(text));
        return ResponseEntity.status(HttpStatus.OK).body(messageDetails);
    }

}
