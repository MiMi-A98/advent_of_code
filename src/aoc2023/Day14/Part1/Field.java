package aoc2023.Day14.Part1;

import java.util.Arrays;

public class Field {
    private char[][] fieldArray;

    public Field(char[][] fieldArray) {
        this.fieldArray = fieldArray;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(fieldArray);
    }

    public char[][] turnNorth() {
        int row;
        int column;
        for (column = 0; column < fieldArray[0].length; column++) {
            boolean move = true;
            while (move) {
                move = false;
                for (row = 1; row < fieldArray.length; row++) {
                    int rockRowUp = row - 1;
                    char character = fieldArray[row][column];
                    Character top = (rockRowUp < 0) ? null : fieldArray[rockRowUp][column];
                    if (character == 'O' && top == '.') {
                        fieldArray[row - 1][column] = 'O';
                        fieldArray[row][column] = '.';
                        move = true;
                    }

                }
            }
        }

        return fieldArray.clone();
    }

    public long calculateBeamsLoad(char[][] turnedField) {
        long beamsLoad = 0;
        int beamNumber = turnedField.length;
        int movableRockCount = 0;
        for (int row = 0; row < turnedField.length; row++) {
            for (int column = 0; column < turnedField[row].length; column++) {
                char character = turnedField[row][column];
                if (character == 'O') {
                    movableRockCount += 1;
                }
            }
            long load = movableRockCount * beamNumber;
            beamsLoad += load;
            beamNumber--;
            movableRockCount = 0;
        }
        return beamsLoad;
    }
}
