package com.anakatech.minesweeper.engine;

import com.anakatech.minesweeper.models.Board;
import com.anakatech.minesweeper.models.BoardImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class EngineImpl implements Engine{

    //TODO add tests for Engine

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        start();
        int level = Integer.parseInt(sc.nextLine());
        Board board = new BoardImpl(level);
        System.out.println(board.toString() + System.lineSeparator() + "Enter your move, (row, column)"+ System.lineSeparator() + "->");
        String[] coordinates = sc.nextLine().split(" ");
        int row = Integer.parseInt(coordinates[0]);
        int col = Integer.parseInt(coordinates[1]);
        board.validateBoard(row, col);
        System.out.println(board.toString());
        while(true){
            System.out.println("Enter your move, (row, column)" + System.lineSeparator() + "-> ");
            coordinates = sc.nextLine().split(" ");
            row = Integer.parseInt(coordinates[0]);
            col = Integer.parseInt(coordinates[1]);
            if(!board.hasNextStep(row, col)){
                System.out.println("Ooops! You exploded!");
                break;
            }else if(board.isWin()){
                System.out.println("Congratulations!!! You WON!!!");
                break;
            }
            System.out.println(board.toString());
        }
    }

    @Override
    public void start() {
        System.out.println("Enter the Difficulty Level");
        System.out.println("Press 1 for BEGINNER (9 * 9 and 10 mines)");
        System.out.println("Press 2 for INTERMEDIATE (16 * 16 and 40 mines)");
        System.out.println("Press 3 for ADVANCED (24 * 24 and 99 mines)");
    }
}
