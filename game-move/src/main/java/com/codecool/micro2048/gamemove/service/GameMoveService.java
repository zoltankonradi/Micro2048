package com.codecool.micro2048.gamemove.service;


import com.codecool.micro2048.gamemove.model.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

        if (!newState.isOver()) {

            shiftEachTile(newState, direction);

            if(!oldState.equals(newState)) placeNewNumber(newState);

            checkWinCondition(newState);
        }

        return newState;
    }

    private void checkWinCondition(GameState state) {

        if (!state.getBoardSetup().contains(0)) {

            boolean isOver = true;
            for (int i = 0; i < state.getBoardSetup().size(); i++) {

                if (tileHasNeighborWithSameValue(state.getBoardSetup(), i)) {
                    isOver = false;
                    break;
                }
            }
            state.setOver(isOver);
        }
    }

    private boolean tileHasNeighborWithSameValue(List<Integer> boardSetup, int index) {
        //Gets the neighbouring indexes from the one dimensional list
        List<Integer> neighbouringIndexes = new ArrayList<>();

        if(index < 12) neighbouringIndexes.add(index + 4);
        if(index > 3) neighbouringIndexes.add(index - 4);
        if(index > 0 && ((index - 1) % 4) != 3) neighbouringIndexes.add(index - 1);
        if(index < 15 && ((index + 1) % 4) != 0) neighbouringIndexes.add(index + 1);

        for (int neighbouringIndex: neighbouringIndexes) {
            if(boardSetup.get(index).equals(boardSetup.get(neighbouringIndex))) return true;
        }
        return false;
    }

    private void placeNewNumber(GameState state) {
        if (state.getBoardSetup().contains(0)) {
            int newNumber = new Random().nextInt(10) == 9 ? 4 : 2;
            List<Integer> emptyIndexes = new ArrayList<>();

            for (int i = 0; i < state.getBoardSetup().size(); i++) {
                if (state.getBoardSetup().get(i) == 0) {
                    emptyIndexes.add(i);
                }
            }
            //Get a random index that has a 0 at its location
            int newNumbersIndex = emptyIndexes.get(new Random().nextInt(emptyIndexes.size()));

            state.getBoardSetup().set(newNumbersIndex, newNumber);
        }
    }

    private void shiftEachTile(GameState state, String direction) {
        // Create a list that stores if each index has a modified value in it
        // [ 0, 0, 0, 0,
        //   0, 0, 0, 0,
        //   4, 4, 4, 4,
        //   4, 4, 4, 4 ]
        // [false, false, false, false,
        //  false, false, false, false,
        //  true, true, true, true,
        //  true, true, true, true ]
        //
        // isIndexModified.get(i) tells us if the value at index i in the
        // boardSetup has been changed in this movement already
        List<Boolean> isIndexModified = new ArrayList<>(Collections.nCopies(16, false));

        for (int i = 0; i < 3; i++) {

            List<Integer> loopValues = getCenterLoopValues(direction, i);

            for (int j: loopValues) {

                for (int k = 0; k < 4; k++) {
                    int currentFieldIndex = getCurrentFieldIndex(direction, j, k);
                    int adjacentFieldIndex = getAdjacentFieldIndex(direction, j, k);

                    List<Integer> fields = state.getBoardSetup();

                    if (!fields.get(currentFieldIndex).equals(0)) {
                        // If the neighboring field is a zero, then replace the current value
                        // with the zero(shiftEachTile it one step in the given direction)
                        // and move the modified flag as well
                        if (fields.get(adjacentFieldIndex).equals(0)) {
                            shiftTile(state, currentFieldIndex, adjacentFieldIndex, isIndexModified);
                        }
                        // If the neighboring field has the same value as the current one, then add this one on top of that
                        // and replace this field's value with a 0
                        // It should not add values together that have been modified in this movement already
                        else if (fields.get(adjacentFieldIndex).equals(fields.get(currentFieldIndex))) {
                            combineTiles(state, currentFieldIndex, adjacentFieldIndex, isIndexModified);
                        }
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


    private void shiftTile(GameState state, int currentFieldIndex, int adjacentFieldIndex, List<Boolean> isIndexModified) {
        List<Integer> fields = state.getBoardSetup();

        fields.set(adjacentFieldIndex, fields.get(currentFieldIndex));
        fields.set(currentFieldIndex, 0);

        isIndexModified.set(adjacentFieldIndex, isIndexModified.get(currentFieldIndex));
        isIndexModified.set(currentFieldIndex, false);
    }

    private void combineTiles(GameState state, int currentFieldIndex, int adjacentFieldIndex, List<Boolean> isIndexModified) {
        List<Integer> fields = state.getBoardSetup();

        if(!isIndexModified.get(adjacentFieldIndex) && !isIndexModified.get(currentFieldIndex)){

            fields.set(adjacentFieldIndex, fields.get(adjacentFieldIndex)*2);
            fields.set(currentFieldIndex, 0);
            state.setScore(state.getScore()+fields.get(adjacentFieldIndex));

            isIndexModified.set(adjacentFieldIndex, true);
            isIndexModified.set(currentFieldIndex, false);
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
