package com.codecool.micro2048.quote.service;

import com.codecool.micro2048.quote.model.JSONData;
import com.codecool.micro2048.quote.model.Quote;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class QuoteService {

//    @Autowired
//    RestTemplate restTemplate;

    @JsonProperty("quotes")
    public String getQuote() {
        RestTemplate restTemplate = new RestTemplate();
        String quoteURL = "https://aitorp6.herokuapp.com/quotes/api/random";
        JSONData quotes = restTemplate.getForObject(quoteURL, JSONData.class);
        System.out.println(quotes);
        return quotes.getQuotes().getQuote();
    }


}
