package com.codecool.micro2048.pictureservice.controller;

import com.codecool.micro2048.pictureservice.service.PictureService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:60001")
public class PictureController {

    @GetMapping("/picture")
    public String getPicture() {
        PictureService pictureService = new PictureService();
        return pictureService.getRandomPicture();
    }

}
