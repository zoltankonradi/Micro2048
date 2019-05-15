package com.codecool.micro2048.pictureservice.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PictureService {

    private static List<String> pictureURLs = new ArrayList<>();

    public void addURL(String url) {
        PictureService.pictureURLs.add(url);
    }

    public String getRandomPicture() {
        Random random = new Random();
        int randomIndex = random.nextInt(pictureURLs.size());
        return PictureService.pictureURLs.get(randomIndex);
    }
}
