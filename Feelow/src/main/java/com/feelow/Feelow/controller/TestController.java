package com.feelow.Feelow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/hello")
public class TestController {

    private final String flaskUrl = "http://43.200.217.72:5001";

    @GetMapping
    public ResponseEntity<String> ex() {
        String response = sendGetRequest();
        return ResponseEntity.ok(response);
    }

    private String sendGetRequest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(flaskUrl, String.class);

        // Here you can process the response as needed
        String responseBody = response.getBody();
        System.out.println("Response from Flask: " + responseBody);

        return responseBody;
    }
}
