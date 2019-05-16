package com.codecool.micro2048.frontendservice.controller.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {
    
    public String getQuote() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8095/quote",String.class);
    }
    
}
