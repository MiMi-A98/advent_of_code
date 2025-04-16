package aoc2023.Day3.Part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    static char[][] inputArray;
    static ArrayList<Integer> validNumbers = new ArrayList<>();

    public static void main(String[] args) {

        String inputFileName = "src/aoc2023.Day3/Input2.txt";

        try {
            // read file first time to determine the number of lines it has, to initialize the 2D array
            FileReader fileReader = new FileReader(inputFileName);
            BufferedReader reader = new BufferedReader(fileReader);

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
            fileReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ArrayList<Integer> digitArray = new ArrayList<>();


        for (int row = 0; row < inputArray.length; row++) {
            for (int column = 0; column < inputArray[row].length; column++) {
                char currentCharacter = inputArray[row][column];
                if (Character.isDigit(currentCharacter)) {
                    // add index (column) of current digit to form later a whole number
                    digitArray.add(column);

                    // validate number if line ends with a digit
                    if (column == inputArray[row].length - 1) {
                        validateNumber(digitArray, row);
                    }
                } else {

                    if (!digitArray.isEmpty()) {
                        validateNumber(digitArray, row);
                    }
                }
            }

        }

        System.out.println(getSum(validNumbers));
    }

    private static void validateNumber(ArrayList<Integer> digitArray, int currentRow) {
        char digit;
        String numberStr = "";

        if (hasNeighbourSymbol(digitArray, currentRow)) {
            for (int digitIndex : digitArray) {
                digit = inputArray[currentRow][digitIndex];
                numberStr += Character.toString(digit);
            }
            if (!numberStr.isEmpty()) {
                int number = Integer.parseInt(numberStr);
                validNumbers.add(number);
            }
        }

        digitArray.clear();
    }

    private static boolean hasNeighbourSymbol(ArrayList<Integer> digitArray, int currentRow) {

        char topLeft;
        char top;
        char topRight;
        char middleLeft;
        char middleRight;
        char bottomLeft;
        char bottom;
        char bottomRight;

        boolean isNeighbourSymbol = false;

        for (int digitIndex : digitArray) {

            if (currentRow == 0) {
                if (digitIndex == 0) {

                    middleRight = inputArray[currentRow][digitIndex + 1];
                    bottom = inputArray[currentRow + 1][digitIndex];
                    bottomRight = inputArray[currentRow + 1][digitIndex + 1];

                    if (isSymbol(middleRight)
                            || isSymbol(bottom)
                            || isSymbol(bottomRight)) {

                        isNeighbourSymbol = true;
                        break;
                    }
                } else if (digitIndex == inputArray[currentRow].length - 1) {
                    middleLeft = inputArray[currentRow][digitIndex - 1];
                    bottomLeft = inputArray[currentRow + 1][digitIndex - 1];
                    bottom = inputArray[currentRow + 1][digitIndex];

                    if (isSymbol(middleLeft)
                            || isSymbol(bottomLeft)
                            || isSymbol(bottom)) {

                        isNeighbourSymbol = true;
                        break;
                    }
                } else {
                    middleLeft = inputArray[currentRow][digitIndex - 1];
                    middleRight = inputArray[currentRow][digitIndex + 1];
                    bottomLeft = inputArray[currentRow + 1][digitIndex - 1];
                    bottom = inputArray[currentRow + 1][digitIndex];
                    bottomRight = inputArray[currentRow + 1][digitIndex + 1];

                    if (isSymbol(middleLeft)
                            || isSymbol(middleRight)
                            || isSymbol(bottomLeft)
                            || isSymbol(bottom)
                            || isSymbol(bottomRight)) {

                        isNeighbourSymbol = true;
                        break;
                    }
                }
            } else if (currentRow == inputArray.length - 1) {
                if (digitIndex == 0) {
                    top = inputArray[currentRow - 1][digitIndex];
                    topRight = inputArray[currentRow - 1][digitIndex + 1];
                    middleRight = inputArray[currentRow][digitIndex + 1];

                    if (isSymbol(top)
                            || isSymbol(topRight)
                            || isSymbol(middleRight)) {

                        isNeighbourSymbol = true;
                        break;
                    }
                } else if (digitIndex == inputArray[currentRow].length - 1) {

                    topLeft = inputArray[currentRow - 1][digitIndex - 1];
                    top = inputArray[currentRow - 1][digitIndex];
                    middleLeft = inputArray[currentRow][digitIndex - 1];

                    if (isSymbol(topLeft)
                            || isSymbol(top)
                            || isSymbol(middleLeft)) {

                        isNeighbourSymbol = true;
                        break;
                    }
                } else {
                    topLeft = inputArray[currentRow - 1][digitIndex - 1];
                    top = inputArray[currentRow - 1][digitIndex];
                    topRight = inputArray[currentRow - 1][digitIndex + 1];
                    middleLeft = inputArray[currentRow][digitIndex - 1];
                    middleRight = inputArray[currentRow][digitIndex + 1];

                    if (isSymbol(topLeft)
                            || isSymbol(top)
                            || isSymbol(topRight)
                            || isSymbol(middleLeft)
                            || isSymbol(middleRight)) {

                        isNeighbourSymbol = true;
                        break;
                    }
                }
            } else if (digitIndex == 0) {
                top = inputArray[currentRow - 1][digitIndex];
                topRight = inputArray[currentRow - 1][digitIndex + 1];
                middleRight = inputArray[currentRow][digitIndex + 1];
                bottom = inputArray[currentRow + 1][digitIndex];
                bottomRight = inputArray[currentRow + 1][digitIndex + 1];

                if (isSymbol(top)
                        || isSymbol(topRight)
                        || isSymbol(middleRight)
                        || isSymbol(bottom)
                        || isSymbol(bottomRight)) {

                    isNeighbourSymbol = true;
                    break;
                }
            } else if (digitIndex == inputArray[currentRow].length - 1) {
                topLeft = inputArray[currentRow - 1][digitIndex - 1];
                top = inputArray[currentRow - 1][digitIndex];
                middleLeft = inputArray[currentRow][digitIndex - 1];
                bottomLeft = inputArray[currentRow + 1][digitIndex - 1];
                bottom = inputArray[currentRow + 1][digitIndex];

                if (isSymbol(topLeft)
                        || isSymbol(top)
                        || isSymbol(middleLeft)
                        || isSymbol(bottomLeft)
                        || isSymbol(bottom)) {

                    isNeighbourSymbol = true;
                    break;
                }
            } else {
                topLeft = inputArray[currentRow - 1][digitIndex - 1];
                top = inputArray[currentRow - 1][digitIndex];
                topRight = inputArray[currentRow - 1][digitIndex + 1];
                middleLeft = inputArray[currentRow][digitIndex - 1];
                middleRight = inputArray[currentRow][digitIndex + 1];
                bottomLeft = inputArray[currentRow + 1][digitIndex - 1];
                bottom = inputArray[currentRow + 1][digitIndex];
                bottomRight = inputArray[currentRow + 1][digitIndex + 1];

                if (isSymbol(topLeft)
                        || isSymbol(top)
                        || isSymbol(topRight)
                        || isSymbol(middleLeft)
                        || isSymbol(middleRight)
                        || isSymbol(bottomLeft)
                        || isSymbol(bottom)
                        || isSymbol(bottomRight)) {

                    isNeighbourSymbol = true;
                    break;
                }
            }
        }

        return isNeighbourSymbol;
    }

    private static boolean isSymbol(char character) {
        return !(Character.isDigit(character) || character == '.');
    }

    private static int getSum(ArrayList<Integer> numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }
}

