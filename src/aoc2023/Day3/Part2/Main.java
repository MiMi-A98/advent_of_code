package aoc2023.Day3.Part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.String.valueOf;

public class Main {
    static char[][] inputArray;
    static ArrayList<Integer> validNumbers = new ArrayList<>();
    static ArrayList<Integer> multiplicationResults = new ArrayList<>();
    static ArrayList<Coordinates> validNumberCoordinates = new ArrayList<>();


    public static void main(String[] args) {

        String inputFileName = "src/aoc2023.Day3/Input2.txt";
        FileReader fileReader = null;
        BufferedReader reader = null;

        try {
            // read file first time to determine the number of lines it has, to initialize the 2D array
            fileReader = new FileReader(inputFileName);
            reader = new BufferedReader(fileReader);

            int inputLineSize = (int) reader.lines().count();
            inputArray = new char[inputLineSize][];

            // read file again to store the values in the 2D array
            fileReader = new FileReader(inputFileName);
            reader = new BufferedReader(fileReader);

            String inputLine;
            int i = 0;
            while ((inputLine = reader.readLine()) != null) {
                inputArray[i] = inputLine.toCharArray();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) fileReader.close();
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        for (int row = 0; row < inputArray.length; row++) {
            for (int column = 0; column < inputArray[row].length; column++) {
                char currentCharacter = inputArray[row][column];

                if (currentCharacter == '*') {
                    findNumberCoordinates(row, column);

                }
            }
        }

        calculateMultiplication(inputArray, validNumberCoordinates);

        System.out.println(getSum(multiplicationResults));

    }

    private static void findNumberCoordinates(int row, int column) {

        ArrayList<Coordinates> numberCoordinates = new ArrayList<>();

        Coordinates numberCoord;

        int symbolRow = row;
        int symbolColumn = column;
        int symbolRowUp = symbolRow - 1;
        int symbolRowDown = symbolRow + 1;
        int symbolColumnLeft = symbolColumn - 1;
        int symbolColumnRight = symbolColumn + 1;

        Character topLeft = (symbolRowUp < 0 || symbolColumnLeft < 0) ? null : inputArray[symbolRowUp][symbolColumnLeft];
        Character top = (symbolRowUp < 0) ? null : inputArray[symbolRowUp][symbolColumn];
        Character topRight = (symbolRowUp < 0 || symbolColumnRight >= inputArray[symbolRow].length) ? null : inputArray[symbolRowUp][symbolColumnRight];
        Character middleLeft = (symbolColumnLeft < 0) ? null : inputArray[symbolRow][symbolColumnLeft];
        Character middleRight = (symbolColumnRight >= inputArray[symbolRow].length) ? null : inputArray[symbolRow][symbolColumnRight];
        Character bottomLeft = (symbolRowDown >= inputArray.length || symbolColumnLeft < 0) ? null : inputArray[symbolRowDown][symbolColumnLeft];
        Character bottom = (symbolRowDown >= inputArray.length) ? null : inputArray[symbolRowDown][symbolColumn];
        Character bottomRight = (symbolRowDown >= inputArray.length || symbolColumnRight >= inputArray[symbolRow].length) ? null : inputArray[symbolRowDown][symbolColumnRight];


        if (top != null && isDigit(top)) {

            numberCoord = extractNumberCoordinates(symbolRowUp, symbolColumn);
            numberCoordinates.add(numberCoord);
        } else {
            if (topLeft != null && isDigit(topLeft)) {

                numberCoord = extractNumberCoordinates(symbolRowUp, symbolColumnLeft);
                numberCoordinates.add(numberCoord);
            }
            if (topRight != null && isDigit(topRight)) {

                numberCoord = extractNumberCoordinates(symbolRowUp, symbolColumnRight);
                numberCoordinates.add(numberCoord);
            }
        }
        if (middleLeft != null && isDigit(middleLeft)) {

            numberCoord = extractNumberCoordinates(symbolRow, symbolColumnLeft);
            numberCoordinates.add(numberCoord);
        }
        if (middleRight != null && isDigit(middleRight)) {

            numberCoord = extractNumberCoordinates(symbolRow, symbolColumnRight);
            numberCoordinates.add(numberCoord);
        }
        if (bottom != null && isDigit(bottom)) {

            numberCoord = extractNumberCoordinates(symbolRowDown, symbolColumn);
            numberCoordinates.add(numberCoord);
        } else {
            if (bottomLeft != null && isDigit(bottomLeft)) {

                numberCoord = extractNumberCoordinates(symbolRowDown, symbolColumnLeft);
                numberCoordinates.add(numberCoord);
            }
            if (bottomRight != null && isDigit(bottomRight)) {

                numberCoord = extractNumberCoordinates(symbolRowDown, symbolColumnRight);
                numberCoordinates.add(numberCoord);
            }
        }

        if (numberCoordinates.size() == 2) {
            validNumberCoordinates.add(numberCoordinates.get(0));
            validNumberCoordinates.add(numberCoordinates.get(1));
            numberCoordinates.clear();
        } else {
            numberCoordinates.clear();

        }

    }

    private static void calculateMultiplication(char[][] inputArray, ArrayList<Coordinates> numberCoordinates) {

        for (Coordinates coordinates : numberCoordinates) {

            String numberString = valueOf(inputArray[coordinates.getRowIndex()], coordinates.getStartNumberColumnIndex(),
                    coordinates.getEndNumberColumnIndex() - coordinates.getStartNumberColumnIndex() + 1);
            int number = Integer.parseInt(numberString);
            validNumbers.add(number);

            if (validNumbers.size() == 2) {

                int multiplicationResult = multiply(validNumbers);
                multiplicationResults.add(multiplicationResult);

                validNumbers.clear();
            }
        }

    }

    private static Coordinates extractNumberCoordinates(int row, int column) {

        int start = lookLeftForDigit(row, column);
        int end = lookRightForDigit(row, column);

        return new Coordinates(row, start, end);
    }


    private static int lookLeftForDigit(int row, int column) {
        int start = 0;
        while (isDigit(inputArray[row][column])) {
            if (column == 0) {
                start = 0;
                break;
            }
            start = column;
            column--;
        }
        return start;
    }

    private static int lookRightForDigit(int row, int column) {
        int end = 0;
        while (isDigit(inputArray[row][column])) {
            if (column == inputArray[row].length - 1) {
                end = inputArray[row].length - 1;
                break;
            }
            end = column;
            column++;
        }
        return end;
    }

    private static boolean isDigit(char character) {

        return Character.isDigit(character);
    }

    private static int getSum(ArrayList<Integer> numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    private static int multiply(ArrayList<Integer> numbers) {
        int multiplicationResult = 1;

        for (int number : numbers) {
            multiplicationResult *= number;
        }
        return multiplicationResult;
    }
}

