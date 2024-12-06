package com.crio.mysqltest.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/hidden-feature")
public class EasterEggController {
    private static final String NUMBERS_API_URL = "http://numbersapi.com/";


    @GetMapping("/{number}")
    public ResponseEntity<String> getNumberFact(@PathVariable int number) {
        RestTemplate restTemplate = new RestTemplate();
        String fact = restTemplate.getForObject(NUMBERS_API_URL + number, String.class);
        return ResponseEntity.ok(fact);
    }
}
