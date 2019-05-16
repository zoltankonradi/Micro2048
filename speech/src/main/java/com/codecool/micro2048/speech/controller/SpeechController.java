package com.codecool.micro2048.speech.controller;

import com.codecool.micro2048.speech.model.Quote;
import com.codecool.micro2048.speech.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:60001")
public class SpeechController {

    @Autowired
    SpeechService speechService;

    @GetMapping("/speechs")
    public Quote getQuote() {
        Quote quote = new Quote();
        quote.setQuote(speechService.getQuoteFromQuoteMicroservise());
        System.out.println(quote);
        return quote;
    }


    @GetMapping("/speech")
    public void getMp3() throws IOException {
        speechService.sendQuoteStringToSpeechApi();
    }
}
