package com.anakatech.minesweeper.models;

import com.anakatech.minesweeper.enums.State;

import java.util.Random;
import static com.anakatech.minesweeper.validations.Validator.*;

public class BoardImpl implements Board {

    private char[][] visualBoard;
    private State[][] stateBoard;
    private int level;
    private int size;
    private int mines;
    private int explored;

    public BoardImpl(int level) {
        setLevel(level);
        setMinesCountByLevel(level);
        setSizeByLevel(level);
        setExplored(0);
        setVisualBoard(fillVisualBoard());
        setStateBoard(fillStateBoard());
    }

    @Override
    public State[][] getStateBoard() {
        return stateBoard;
    }

    //TODO fix explored counter and replace it, instead of iterating 2d array and count left mines
    @Override
    public boolean isWin(){
//        return getExplored() >= (size * size) - mines;
        int mineCounter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(visualBoard[i][j] == '-'){
                    mineCounter++;
                }
            }
        }
        return mineCounter == mines;
    }

    public void setVisualBoard(char[][] visualBoard) {
        this.visualBoard = visualBoard;
    }

    public void setStateBoard(State[][] stateBoard) {
        this.stateBoard = stateBoard;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void revealZeros(int row, int col) {
        if (checkOutOfBound(row, col, size) || stateBoard[row][col] == State.MINED || getSurroundingMines(row, col) > 0) {
            return;
        }
        if (visualBoard[row][col] != '-') {
            return;
        }else if(visualBoard[row][col] == '-') {
            reworkVisualBoard(row, col);
            revealZeros(row, col-2);
            revealZeros(row-2, col);
            revealZeros(row+2, col);
            revealZeros(row, col+2);
        }

    }

    public boolean hasNextStep(int row, int col){
        if(stateBoard[row][col] != State.MINED){
            if(getSurroundingMines(row, col) > 0){
                visualBoard[row][col] = (char)(getSurroundingMines(row, col)+'0');
                explored++;
            }else{
                revealZeros(row, col);
            }
        }else{
            return false;
        }
        return true;
    }

	@Override
    public int getSurroundingMines(int row, int col)
    {
        int counter = 0;
        for (int dx=-1; dx<=1; dx++)
        {
            for (int dy=-1; dy<=1; dy++)
            {
                if (dx == 0 && dy == 0)
                {
                    continue;
                }
                int nx = row+dx;
                int ny = col+dy;
                if (!validateLength(nx, size) && validateLength(ny, size))
                {
                    continue;
                }
                if (!checkOutOfBound(nx,ny,size) && stateBoard[nx][ny] == State.MINED)
                {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public String toString() {
	    StringBuilder str = new StringBuilder();
	    str.append("Current status of Board :").append(System.lineSeparator());
        for (int i = 0; i < size; i++) {
            if(i == 0){
                str.append("    ");
                for (int j = 0; j < size; j++) {
                    if (j < 10) {
                        str.append(j).append("  ");
                    } else {
                        str.append(j).append(" ");
                    }
                }
                str.append(System.lineSeparator());
            }
            if(i < 10){
                str.append(i).append("   ");
            }else{
                str.append(i).append("  ");
            }

            for (int j = 0; j < size; j++) {
                str.append(visualBoard[i][j]).append("  ");
            }
            str.append(System.lineSeparator());
        }
        return str.toString();
    }




    //Validate board after first move

    @Override
    public void validateBoard(int row, int col){
        if(stateBoard[row][col] != State.MINED){
            if(getSurroundingMines(row, col) == 0){
                revealZeros(row, col);
            }else{
                visualBoard[row][col] = (char)(getSurroundingMines(row, col)+'0');
                explored++;
            }

            return;
        }else{
            stateBoard[row][col] = State.CLEARED;
            Random rnd = new Random();
            while(true){
                int rndRow = rnd.nextInt(size);
                int rndCol = rnd.nextInt(size);
                if(stateBoard[rndRow][rndCol] != State.MINED &&
                        (rndRow != row || rndCol != col)){
                    stateBoard[rndRow][rndCol] = State.MINED;
                    break;
                }
            }
        }
    }
    public State[][] fillStateBoard(){
	    State[][] stateBoard = new State[size][size];
        Random rnd = new Random();
        for (int i = 0; i < mines; i++) {
            while(true){
                int rndRow = rnd.nextInt(size);
                int rndCol = rnd.nextInt(size);
                if(stateBoard[rndRow][rndCol] != State.MINED){
                    stateBoard[rndRow][rndCol] = State.MINED;
                    break;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(stateBoard[i][j] == null){
                    stateBoard[i][j] = State.UNEXPLORED;
                }
            }
        }
        return stateBoard;
    }

    public char[][] fillVisualBoard(){
	    char[][] board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '-';
            }
        }
        return board;
    }
    private void reworkVisualBoard(int row, int col){
        for (int dx=-1; dx<=1; dx++)
        {
            for (int dy=-1; dy<=1; dy++)
            {
                int nx = row+dx;
                int ny = col+dy;
                if(!checkOutOfBound(nx, ny, size) && visualBoard[nx][ny] == '-'){
                    visualBoard[nx][ny] = '+';
                    explored++;
                }
            }
        }
    }

    private void setSizeByLevel(int level){
        switch (level){
            case 1: setSize(9); break;
            case 2: setSize(16); break;
            case 3: setSize(24); break;
        }
    }

    private void setMinesCountByLevel(int level){
        switch (level){
            case 1: setMines(10); break;
            case 2: setMines(40); break;
            case 3: setMines(99); break;
        }
    }

    private void setSize(int size) {
        this.size = size;
    }


    private void setExplored(int explored) {
        this.explored = explored;
    }

    private void setMines(int mines) {
        this.mines = mines;
    }
}
