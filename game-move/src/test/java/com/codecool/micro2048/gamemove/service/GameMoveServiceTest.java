package com.codecool.micro2048.gamemove.service;

import com.codecool.micro2048.gamemove.model.GameState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class GameMoveServiceTest {

    @Autowired
    private GameMoveService gameMoveService;

    @Test
    public void testSimpleMoveLeft() {
        List<Integer> initialBoardSetup = Arrays.asList(
                0, 0, 2, 0,
                0, 2, 0, 0,
                2, 0, 0, 0,
                0, 0, 0, 2);
        GameState actual = new GameState(initialBoardSetup, 0);

        List<Integer> expectedBoardSetup = Arrays.asList(
                2, 0, 0, 0,
                2, 0, 0, 0,
                2, 0, 0, 0,
                2, 0, 0, 0);
        GameState expected = new GameState(expectedBoardSetup, 0);

        actual = gameMoveService.calculateMovement("left", actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testSimpleMoveRight() {
        List<Integer> initialBoardSetup = Arrays.asList(
                0, 0, 2, 0,
                0, 2, 0, 0,
                2, 0, 0, 0,
                0, 0, 0, 2);
        GameState actual = new GameState(initialBoardSetup, 0);

        List<Integer> expectedBoardSetup = Arrays.asList(
                0, 0, 0, 2,
                0, 0, 0, 2,
                0, 0, 0, 2,
                0, 0, 0, 2);
        GameState expected = new GameState(expectedBoardSetup, 0);

        actual = gameMoveService.calculateMovement("right", actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testSimpleMoveUp() {
        List<Integer> initialBoardSetup = Arrays.asList(
                0, 0, 2, 0,
                0, 2, 0, 0,
                2, 0, 0, 0,
                0, 0, 0, 2);
        GameState actual = new GameState(initialBoardSetup, 0);

        List<Integer> expectedBoardSetup = Arrays.asList(
                2, 2, 2, 2,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0);
        GameState expected = new GameState(expectedBoardSetup, 0);

        actual = gameMoveService.calculateMovement("up", actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testSimpleMoveDown() {
        List<Integer> initialBoardSetup = Arrays.asList(
                0, 0, 2, 0,
                0, 2, 0, 0,
                2, 0, 0, 0,
                0, 0, 0, 2);
        GameState actual = new GameState(initialBoardSetup, 0);

        List<Integer> expectedBoardSetup = Arrays.asList(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                2, 2, 2, 2);
        GameState expected = new GameState(expectedBoardSetup, 0);

        actual = gameMoveService.calculateMovement("down", actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testAddingMoveDown() {
        List<Integer> initialBoardSetup = Arrays.asList(
                2, 2, 2, 2,
                2, 2, 2, 2,
                2, 2, 2, 2,
                2, 2, 2, 2);
        GameState actual = new GameState(initialBoardSetup, 0);

        List<Integer> expectedBoardSetup = Arrays.asList(
                0, 0, 0, 0,
                0, 0, 0, 0,
                4, 4, 4, 4,
                4, 4, 4, 4);
        GameState expected = new GameState(expectedBoardSetup, 32);

        actual = gameMoveService.calculateMovement("down", actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testAddingMoveUp() {
        List<Integer> initialBoardSetup = Arrays.asList(
                2, 2, 2, 2,
                2, 2, 2, 2,
                2, 2, 2, 2,
                2, 2, 2, 2);
        GameState actual = new GameState(initialBoardSetup, 0);

        List<Integer> expectedBoardSetup = Arrays.asList(
                4, 4, 4, 4,
                4, 4, 4, 4,
                0, 0, 0, 0,
                0, 0, 0, 0);
        GameState expected = new GameState(expectedBoardSetup, 32);

        actual = gameMoveService.calculateMovement("up", actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testAddingMoveLeft() {
        List<Integer> initialBoardSetup = Arrays.asList(
                2, 2, 2, 2,
                2, 2, 2, 2,
                2, 2, 2, 2,
                2, 2, 2, 2);
        GameState actual = new GameState(initialBoardSetup, 0);

        List<Integer> expectedBoardSetup = Arrays.asList(
                4, 4, 0, 0,
                4, 4, 0, 0,
                4, 4, 0, 0,
                4, 4, 0, 0);
        GameState expected = new GameState(expectedBoardSetup, 32);

        actual = gameMoveService.calculateMovement("left", actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testAddingMoveRight() {
        List<Integer> initialBoardSetup = Arrays.asList(
                2, 2, 2, 2,
                2, 2, 2, 2,
                2, 2, 2, 2,
                2, 2, 2, 2);
        GameState actual = new GameState(initialBoardSetup, 0);

        List<Integer> expectedBoardSetup = Arrays.asList(
                0, 0, 4, 4,
                0, 0, 4, 4,
                0, 0, 4, 4,
                0, 0, 4, 4);
        GameState expected = new GameState(expectedBoardSetup, 32);

        actual = gameMoveService.calculateMovement("right", actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testWinConditionIsTrue() {
        List<Integer> initialBoardSetup = Arrays.asList(
                1, 2, 3, 4,
                4, 5, 6, 7,
                7, 8, 9, 10,
                10, 11, 12, 13);
        GameState actual = new GameState(initialBoardSetup, 0);
        actual = gameMoveService.calculateMovement("left", actual);

        assertTrue(actual.isOver());
    }

    @Test
    public void testWinConditionIsFalse() {
        List<Integer> initialBoardSetup = Arrays.asList(
                2, 2, 2, 2,
                4, 4, 4, 4,
                8, 8, 8, 8,
                16, 16, 16, 16);
        GameState actual = new GameState(initialBoardSetup, 0);
        actual = gameMoveService.calculateMovement("down", actual);

        assertFalse(actual.isOver());
    }

    @Test
    public void testMultipleTilesAdding() {
        List<Integer> initialBoardSetup = Arrays.asList(
                0, 0, 0, 0,
                4, 0, 0, 0,
                4, 0, 0, 0,
                8, 0, 0, 0);
        GameState actual = new GameState(initialBoardSetup, 0);

        List<Integer> expectedBoardSetup = Arrays.asList(
                0, 0, 0, 0,
                0, 0, 0, 0,
                8, 0, 0, 0,
                8, 0, 0, 0);
        GameState expected = new GameState(expectedBoardSetup, 0);

        actual = gameMoveService.calculateMovement("down", actual);

        assertEquals(expected, actual);
    }
}