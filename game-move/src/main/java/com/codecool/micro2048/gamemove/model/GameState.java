package com.codecool.micro2048.gamemove.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
@Slf4j
@NoArgsConstructor
public class GameState {
    private List<Integer> boardSetup = new ArrayList<>();
    private boolean isOver = false;
    private Integer score = 0;

    public GameState(String boardSetupString, Integer score) {
        try {
            List<String> boardSetupWithStrings = Arrays.asList(boardSetupString.split(","));
            this.boardSetup = boardSetupWithStrings
                    .stream()
                    .mapToInt(Integer::valueOf)
                    .boxed()
                    .collect(Collectors.toList());
            this.score = score;
        } catch (NumberFormatException e) {
            log.error("Game state string couldn't be parsed into integer list: " + boardSetupString);
        }
    }

    public GameState(List<Integer> boardSetup, Integer score) {
        if(boardSetup.size()!=16) throw new IllegalArgumentException("Not enough numbers to fill the board.");
        this.boardSetup = new ArrayList<>();
        this.boardSetup.addAll(boardSetup);
        this.score = score;
    }

    public GameState(GameState state) {
        this.boardSetup = new ArrayList<>();
        this.boardSetup.addAll(state.getBoardSetup());
        this.isOver = state.isOver();
        this.score = state.getScore();
    }
}

