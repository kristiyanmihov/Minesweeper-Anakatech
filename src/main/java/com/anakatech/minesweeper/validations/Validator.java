package com.anakatech.minesweeper.validations;

public class Validator {
    public static boolean validateLength(int x, int max)
    {
        return x >= 0 && x < max;
    }

    public static boolean checkOutOfBound(int row, int col, int size){
        if(row < 0 || col >= size || col < 0 || row >= size){
            return true;
        }else{
            return false;
        }
    }
}
