package com.codecool.micro2048.gamemove;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GameMoveApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameMoveApplication.class, args);
    }

}
