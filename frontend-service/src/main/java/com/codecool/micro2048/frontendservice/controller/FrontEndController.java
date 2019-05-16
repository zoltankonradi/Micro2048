package com.codecool.micro2048.frontendservice.controller;

import com.codecool.micro2048.frontendservice.controller.service.QuoteService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {

    @GetMapping(value = "/")
    public String getGame(Model model) {
        QuoteService quoteService = new QuoteService();
        JSONObject json = new JSONObject(quoteService.getQuote());
        model.addAttribute("quote", json.getString("quote"));
        return "game";
    }

}
