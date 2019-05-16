package com.codecool.micro2048.frontendservice.service;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class NewBoardGenerator {

    public int[] generateNewBoard() {
        int[] newBoard = new int[16];
        Random random = new Random();
        int index1 = random.nextInt(16);
        int index2 = random.nextInt(16);
        while (index2 == index1) {
            index2 = random.nextInt(16);
        }
        newBoard[index1] = 2;
        newBoard[index2] = 2;
        return newBoard;
    }

}
