package com.codecool.micro2048.frontendservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {

    @GetMapping(value = "/")
    public String getGame() {
        return "game";
    }

}
