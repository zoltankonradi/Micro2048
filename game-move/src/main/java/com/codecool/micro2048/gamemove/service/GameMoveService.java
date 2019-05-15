package com.codecool.micro2048.gamemove.service;


import com.codecool.micro2048.gamemove.model.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GameMoveService {

    public GameState calculateMovement(String direction, GameState oldState) {
        if (!validateDirectionInput(direction)) return null;
        GameState newState = new GameState(oldState);

        // Game board modelled like this:
        //  0  1  2  3
        //  4  5  6  7
        //  8  9 10 11
        // 12 13 14 15
        // as it is a simple array, not a multi-dimensional one

        move(newState, direction);

        return newState;
    }

    private void move(GameState state, String direction) {
        // First we have to fill all the gaps
        shift(state, direction, false);
        // Second, we have to combine adjacent tiles with the same values
        shift(state, direction, true);
        // Third, we have to fill the gaps that are left after tiles have been combined
        shift(state, direction, false);
    }

    private void shift(GameState state, String direction, boolean canCombineTiles) {
        for (int i = 0; i < 3; i++) {

            List<Integer> loopValues = getCenterLoopValues(direction, i);

            for (int j: loopValues) {

                for (int k = 0; k < 4; k++) {
                    int currentFieldIndex = getCurrentFieldIndex(direction, j, k);
                    int adjacentFieldIndex = getAdjacentFieldIndex(direction, j, k);

                    if (canCombineTiles) {
                        combineFields(state, currentFieldIndex, adjacentFieldIndex);
                    } else {
                        shiftField(state, currentFieldIndex, adjacentFieldIndex);
                    }
                }
            }
        }
    }

    private List<Integer> getCenterLoopValues(String direction, int i) {
        List<Integer> loopValues = new ArrayList<>();
        switch (direction) {
            case "down":
                for (int j = 8; j > (-1 + i * 4); j-=4) {
                    loopValues.add(j);
                }
                break;
            case "up":
                for (int j = 4; j < (13 - i * 4); j += 4) {
                    loopValues.add(j);
                }
                break;
            case "left":
                for (int j = 13; j < 16 - i; j++) {
                    loopValues.add(j);
                }
                break;
            case "right":
                for (int j = 14; j > 11 + i; j--) {
                    loopValues.add(j);
                }
                break;
        }
        return loopValues;
    }

    private int getCurrentFieldIndex(String direction, int j, int k) {
        int currentFieldIndex = -1;
        switch (direction) {
            case "down":
            case "up":
                currentFieldIndex = j + k;
                break;
            case "left":
            case "right":
                currentFieldIndex = j - k * 4;
                break;
        }
        return currentFieldIndex;
    }

    private int getAdjacentFieldIndex(String direction, int j, int k) {
        int adjacentFieldIndex = -1;
        switch (direction) {
            case "down":
                adjacentFieldIndex = j+k+4;
                break;
            case "up":
                adjacentFieldIndex = j + k - 4;
                break;
            case "left":
                adjacentFieldIndex = j - k * 4 - 1;
                break;
            case "right":
                adjacentFieldIndex = j - k * 4 + 1;
                break;
        }
        return adjacentFieldIndex;
    }


    private void shiftField(GameState state, int currentFieldIndex, int adjacentFieldIndex) {
        List<Integer> fields = state.getBoardSetup();
        if (!fields.get(currentFieldIndex).equals(0)) {
            if (fields.get(adjacentFieldIndex).equals(0)) {
                fields.set(adjacentFieldIndex, fields.get(currentFieldIndex));
                fields.set(currentFieldIndex, 0);
            }
        }
    }

    private void combineFields(GameState state, int currentFieldIndex, int adjacentFieldIndex) {
        List<Integer> fields = state.getBoardSetup();
        if (fields.get(adjacentFieldIndex).equals(fields.get(currentFieldIndex))) {
            fields.set(adjacentFieldIndex, fields.get(adjacentFieldIndex)*2);
            fields.set(currentFieldIndex, 0);
            state.setScore(state.getScore()+fields.get(adjacentFieldIndex));
        }
    }

    private boolean validateDirectionInput(String direction) {
        if (direction==null || !(direction.equals("left") ||
                direction.equals("right") ||
                direction.equals("up") ||
                direction.equals("down"))) {
            log.error("Direction string didn't match predefined directions: " + direction);
            return false;
        } else {
            log.info("Movement direction: " + direction);
            return true;
        }
    }
}
