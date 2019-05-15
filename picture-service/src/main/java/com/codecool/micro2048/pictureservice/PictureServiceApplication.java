package com.codecool.micro2048.pictureservice;

import com.codecool.micro2048.pictureservice.service.PictureService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class PictureServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            PictureService pictureService = new PictureService();
            pictureService.addURL("https://images.wallpaperscraft.com/image/cat_stretch_look_striped_31829_1920x1080.jpg");
            pictureService.addURL("https://images.wallpaperscraft.com/image/cat_computer_curiosity_56487_1920x1080.jpg");
            pictureService.addURL("https://images.wallpaperscraft.com/image/cat_tabby_blur_107696_1920x1080.jpg");
            pictureService.addURL("https://images.wallpaperscraft.com/image/cat_muzzle_eyes_84045_1920x1080.jpg");
            pictureService.addURL("https://images.wallpaperscraft.com/image/cat_grass_sit_84130_1920x1080.jpg");
            pictureService.addURL("https://images.wallpaperscraft.com/image/cats_couple_fluffy_108019_1920x1080.jpg");
            pictureService.addURL("https://images.wallpaperscraft.com/image/cat_fluffy_beautiful_117851_1920x1080.jpg");
            pictureService.addURL("https://images.wallpaperscraft.com/image/cat_fat_look_72684_1920x1080.jpg");
        };
    }
}