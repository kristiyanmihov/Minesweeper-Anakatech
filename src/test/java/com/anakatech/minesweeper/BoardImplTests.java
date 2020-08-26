package com.anakatech.minesweeper;

import com.anakatech.minesweeper.engine.Engine;
import com.anakatech.minesweeper.engine.EngineImpl;
import com.anakatech.minesweeper.enums.State;
import com.anakatech.minesweeper.models.Board;
import com.anakatech.minesweeper.models.BoardImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BoardImplTests {
    Board testBoardLevel1 = new BoardImpl(1);
    Board testBoardLevel3 = new BoardImpl(3);

    @Before
    public void before(){
        Engine engine = new EngineImpl();
    }

    @Test
    public void execute_Should_loseTheGame_When_Exploded() {
        State[][] stateBoard = testBoardLevel1.getStateBoard();
        Random random = new Random();
        String message;
        while(true){
            int rndRow = random.nextInt(testBoardLevel1.getSize());
            int rndCol = random.nextInt(testBoardLevel1.getSize());
            if(stateBoard[rndRow][rndCol] == State.MINED){
                assertEquals(stateBoard[rndRow][rndCol] == State.MINED, !testBoardLevel1.isWin());
                break;
            }
        }
    }

    @Test
    public void execute_Should_WinTheGame_When_All_Cleared_Explored() {
        int explored = 0;
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                if(testBoardLevel3.getStateBoard()[i][j] != State.MINED){
                    testBoardLevel3.hasNextStep(i, j);
                }
            }
        }
        assertTrue(testBoardLevel3.isWin());
    }

}
