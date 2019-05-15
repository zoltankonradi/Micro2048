package com.codecool.micro2048.quote.controller;

import com.codecool.micro2048.quote.model.Quote;
import com.codecool.micro2048.quote.service.QuoteService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {

    @Autowired
    QuoteService quoteService;

    @GetMapping("/quote")
    public Quote quote() {
        return new Quote(
                quoteService.getQuote()
        );
    }

    @Data
    @AllArgsConstructor
    public class Quote {
        private String quote;
    }

}
