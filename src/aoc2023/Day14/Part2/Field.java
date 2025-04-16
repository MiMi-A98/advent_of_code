package aoc2023.Day14.Part2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Field {

    private char[][] fieldArray;

    public Field() {
    }

    public Field(char[][] fieldArray) {
        this.fieldArray = fieldArray;
    }

    public char[][] getFieldArray() {
        return fieldArray;
    }

    public long findFinalLoad() {

        Map<Field, Long> platformCache = new HashMap<>();
        int cycleCount = 1000000000;
        long count = 1;

        Field turnedField = new Field();

        for (count = 1; count <= cycleCount; count++) {
            turnedField = new Field(makeCompleteCycle());
            if (platformCache.containsKey(turnedField)) {
                long delta = count - platformCache.get(turnedField);
                count += delta * ((cycleCount - count) / delta);
            }
            platformCache.put(turnedField, count);
        }
        return calculateBeamsLoad(turnedField.getFieldArray());
    }

    private long calculateBeamsLoad(char[][] turnedField) {
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

    private char[][] makeCompleteCycle() {

        turnNorth();
        turnWest();
        turnSouth();
        turnEast();

        return fieldArray.clone();
    }

    private char[][] turnNorth() {
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

    private char[][] turnSouth() {
        int row;
        int column;
        for (column = 0; column < fieldArray[0].length; column++) {
            boolean move = true;
            while (move) {
                move = false;
                for (row = 0; row < fieldArray.length - 1; row++) {
                    int rockRowDown = row + 1;
                    char character = fieldArray[row][column];
                    Character down = (rockRowDown >= fieldArray.length) ? null : fieldArray[rockRowDown][column];
                    if (character == 'O') {
                        if (down == '.') {
                            fieldArray[row + 1][column] = 'O';
                            fieldArray[row][column] = '.';
                            move = true;
                        }

                    }

                }
            }
        }
        return fieldArray.clone();
    }

    private char[][] turnEast() {
        int row;
        int column;
        for (row = 0; row < fieldArray.length; row++) {
            boolean move = true;
            while (move) {
                move = false;
                for (column = 0; column < fieldArray[row].length - 1; column++) {
                    int rockColumnRight = column + 1;
                    char character = fieldArray[row][column];
                    Character right = (rockColumnRight >= fieldArray[row].length) ? null : fieldArray[row][rockColumnRight];
                    if (character == 'O' && right == '.') {
                        fieldArray[row][column + 1] = 'O';
                        fieldArray[row][column] = '.';
                        move = true;
                    }
                }
            }
        }
        return fieldArray.clone();
    }

    private char[][] turnWest() {
        int row;
        int column;
        for (row = 0; row < fieldArray.length; row++) {
            boolean move = true;
            while (move) {
                move = false;
                for (column = fieldArray[row].length - 1; column > 0; column--) {
                    int rockColumnLeft = column - 1;
                    char character = fieldArray[row][column];
                    Character right = fieldArray[row][rockColumnLeft];
                    if (character == 'O') {
                        if (right == '.') {
                            fieldArray[row][rockColumnLeft] = 'O';
                            fieldArray[row][column] = '.';
                            move = true;
                        }

                    }
                }
            }
        }
        return fieldArray.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        return Arrays.deepEquals(fieldArray, field.fieldArray);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(fieldArray);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(fieldArray);
    }
}
