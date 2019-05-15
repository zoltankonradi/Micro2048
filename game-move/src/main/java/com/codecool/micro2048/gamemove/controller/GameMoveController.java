package com.codecool.micro2048.gamemove.controller;


import com.codecool.micro2048.gamemove.model.GameState;
import com.codecool.micro2048.gamemove.service.GameMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:60001")
public class GameMoveController {

    @Autowired
    private GameMoveService gameMoveService;

    @GetMapping("/new-state")
    public GameState getNewState(@RequestParam("direction") String direction,
                                 @RequestParam("boardSetup") String boardSetup,
                                 @RequestParam("score") Integer score) {
        GameState oldState = new GameState(boardSetup, score);
        GameState newState = gameMoveService.calculateMovement(direction, oldState, score);

        return newState;
    }

}
