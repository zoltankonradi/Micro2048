package com.codecool.micro2048.frontendservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PictureService {

    public List<String> getRandomPictures() {
        RestTemplate restTemplate = new RestTemplate();
        List<String> pictures = new ArrayList<>();
        pictures.add(restTemplate.getForObject("http://localhost:60002/picture",String.class));
        pictures.add(restTemplate.getForObject("http://localhost:60002/picture",String.class));
        pictures.add(restTemplate.getForObject("http://localhost:60002/picture",String.class));
        pictures.add(restTemplate.getForObject("http://localhost:60002/picture",String.class));
        pictures.add(restTemplate.getForObject("http://localhost:60002/picture",String.class));
        return pictures;
    }

}
