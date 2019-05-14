package com.codecool.micro2048.gamemove.service;


import com.codecool.micro2048.gamemove.model.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GameMoveService {

    public GameState calculateMovement(String direction, GameState oldState, Integer score) {
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

//        switch (direction) {
//            case "left":
//                moveLeft(state);
//                break;
//            case "right":
//                moveRight(state);
//                break;
//            case "up":
//                moveUp(state);
//                break;
//            case "down":
//                moveDown(state);
//                break;
//        }
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
                        combineFields(state.getBoardSetup(), currentFieldIndex, adjacentFieldIndex, state.getScore());
                    } else {
                        shiftField(state.getBoardSetup(), currentFieldIndex, adjacentFieldIndex, state.getScore());
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



    private void moveDown(GameState state) {
        shiftDown(state);
        combineDown(state);
        shiftDown(state);
    }

    private void shiftDown(GameState state) {
        for (int i = 0; i < 3; i++) {

            for (int j = 8; j > (-1 + i * 4); j-=4) {

                for (int k = 0; k < 4; k++) {

                    int currentFieldIndex = j + k;
                    int adjacentFieldIndex = j + k + 4;

                    shiftField(state.getBoardSetup(), currentFieldIndex, adjacentFieldIndex, state.getScore());
                }
            }
        }
    }

    private void combineDown(GameState state) {
        for (int i = 0; i < 3; i++) {

            for (int j = 8; j > (-1 + i * 4); j-=4) {

                for (int k = 0; k < 4; k++) {

                    int currentFieldIndex = j + k;
                    int adjacentFieldIndex = j + k + 4;

                    combineFields(state.getBoardSetup(), currentFieldIndex, adjacentFieldIndex, state.getScore());
                }
            }
        }
    }


    private void moveUp(GameState state) {
        shiftUp(state);
        combineUp(state);
        shiftUp(state);
    }

    private void shiftUp(GameState state) {
        for (int i = 0; i < 3; i++) {

            for (int j = 4; j < (13 - i * 4); j+=4) {

                for (int k = 0; k < 4; k++) {

                    int currentFieldIndex = j + k;
                    int adjacentFieldIndex = j + k - 4;

                    shiftField(state.getBoardSetup(), currentFieldIndex, adjacentFieldIndex, state.getScore());
                }
            }
        }
    }

    private void combineUp(GameState state) {
        for (int i = 0; i < 3; i++) {

            for (int j = 4; j < (13 - i * 4); j+=4) {

                for (int k = 0; k < 4; k++) {

                    int currentFieldIndex = j + k;
                    int adjacentFieldIndex = j + k - 4;

                    combineFields(state.getBoardSetup(), currentFieldIndex, adjacentFieldIndex, state.getScore());
                }
            }
        }
    }


    private void moveLeft(GameState state) {
        shiftLeft(state);
        combineLeft(state);
        shiftLeft(state);
    }

    private void shiftLeft(GameState state) {
        for(int i = 0; i<3; i++){

            for (int j = 13; j < 16 - i; j++) {

                for (int k = 0; k < 4; k++) {

                    int currentFieldIndex = j - k * 4;
                    int adjacentFieldIndex = j - k * 4 - 1;

                    shiftField(state.getBoardSetup(), currentFieldIndex, adjacentFieldIndex, state.getScore());
                }
            }
        }
    }

    private void combineLeft(GameState state) {
        for(int i = 0; i<3; i++){

            for (int j = 13; j < 16 - i; j++) {

                for (int k = 0; k < 4; k++) {

                    int currentFieldIndex = j - k * 4;
                    int adjacentFieldIndex = j - k * 4 - 1;

                    combineFields(state.getBoardSetup(), currentFieldIndex, adjacentFieldIndex, state.getScore());
                }
            }
        }
    }


    private void moveRight(GameState state) {
        shiftRight(state);
        combineRight(state);
        shiftRight(state);
    }

    private void shiftRight(GameState state) {
        for (int i = 0; i < 3; i++) {

            for (int j = 14; j > 11 + i; j--) {

                for (int k = 0; k < 4; k++) {

                    int currentFieldIndex = j - k * 4;
                    int adjacentFieldIndex = j - k * 4 + 1;

                    shiftField(state.getBoardSetup(), currentFieldIndex, adjacentFieldIndex, state.getScore());
                }
            }
        }
    }

    private void combineRight(GameState state) {
        for (int i = 0; i < 3; i++) {

            for (int j = 14; j > 11 + i; j--) {

                for (int k = 0; k < 4; k++) {

                    int currentFieldIndex = j - k * 4;
                    int adjacentFieldIndex = j - k * 4 + 1;

                    combineFields(state.getBoardSetup(), currentFieldIndex, adjacentFieldIndex, state.getScore());
                }
            }
        }
    }


    private void shiftField(List<Integer> fields, int currentFieldIndex, int adjacentFieldIndex, Integer score) {
        if (!fields.get(currentFieldIndex).equals(0)) {
            if (fields.get(adjacentFieldIndex).equals(0)) {
                fields.set(adjacentFieldIndex, fields.get(currentFieldIndex));
                fields.set(currentFieldIndex, 0);
            }
        }
    }

    private void combineFields(List<Integer> fields, int currentFieldIndex, int adjacentFieldIndex, Integer score) {
        if (fields.get(adjacentFieldIndex).equals(fields.get(currentFieldIndex))) {
            fields.set(adjacentFieldIndex, fields.get(adjacentFieldIndex)*2);
            fields.set(currentFieldIndex, 0);
            score+=fields.get(adjacentFieldIndex)*2;
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
