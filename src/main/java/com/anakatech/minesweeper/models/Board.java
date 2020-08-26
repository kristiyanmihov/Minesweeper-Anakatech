package com.anakatech.minesweeper.models;

import com.anakatech.minesweeper.enums.State;

public interface Board {
    State[][] getStateBoard();
    int getSurroundingMines(int row, int col);
    int getSize();
    boolean isWin();
    void validateBoard(int row, int col);
    boolean hasNextStep(int row, int col);
    void revealZeros(int row, int col);
}
