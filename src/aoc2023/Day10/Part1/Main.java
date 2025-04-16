package aoc2023.Day10.Part1;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    static char[][] inputArray;
    static int stepsCounter = 0;

    public static void main(String[] args) {
        String inputFileName = "src/aoc2023.Day10/Part1/Input2.txt";

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
            reader.close();
            fileReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int[] startPoint = findStartingPoint();
        Coordinates startCoordinates = new Coordinates(startPoint[0], startPoint[1]);
        Coordinates currentCoordinates = startCoordinates;

        Coordinates topCoordinates = new Coordinates(currentCoordinates.row() - 1, currentCoordinates.column());
        Coordinates bottomCoordinates = new Coordinates(currentCoordinates.row() + 1, currentCoordinates.column());
        Coordinates leftCoordinates = new Coordinates(currentCoordinates.row(), currentCoordinates.column() - 1);
        Coordinates rightCoordinates = new Coordinates(currentCoordinates.row(), currentCoordinates.column() + 1);

        Character top = (currentCoordinates.row() - 1 < 0) ? null : inputArray[currentCoordinates.row() - 1][currentCoordinates.column()];
        Character bottom = (currentCoordinates.row() + 1 >= inputArray.length) ? null : inputArray[currentCoordinates.row() + 1][currentCoordinates.column()];
        Character left = (currentCoordinates.column() - 1 < 0) ? null : inputArray[currentCoordinates.row()][currentCoordinates.column() - 1];
        Character right = (currentCoordinates.column() + 1 >= inputArray[currentCoordinates.row()].length) ? null : inputArray[currentCoordinates.row()][currentCoordinates.column() + 1];

        if (top == '|' || top == '7' || top == 'F') {
            currentCoordinates = topCoordinates;
        } else if (bottom == '|' || bottom == 'L' || bottom == 'J') {
            currentCoordinates = bottomCoordinates;
        } else if (left == '-' || left == 'L' || left == 'F') {
            currentCoordinates = leftCoordinates;
        } else if (right == '-' || right == 'J' || right == '7') {
            currentCoordinates = rightCoordinates;
        }
        stepsCounter++;

        Coordinates previousCoordinates = startCoordinates;
        Coordinates nextCoordinates = null;

        while (!currentCoordinates.equals(startCoordinates)) {
            Coordinates topOfCurrentCoordinates = new Coordinates(currentCoordinates.row() - 1, currentCoordinates.column());
            Coordinates bottomOfCurrentCoordinates = new Coordinates(currentCoordinates.row() + 1, currentCoordinates.column());
            Coordinates leftOfCurrentCoordinates = new Coordinates(currentCoordinates.row(), currentCoordinates.column() - 1);
            Coordinates rightOfCurrentCoordinates = new Coordinates(currentCoordinates.row(), currentCoordinates.column() + 1);

            char currentCharacter = readCharacter(currentCoordinates);
            if (currentCharacter == '|') {
                nextCoordinates = (previousCoordinates.equals(topOfCurrentCoordinates)) ? bottomOfCurrentCoordinates : topOfCurrentCoordinates;

            } else if (currentCharacter == '7') {
                nextCoordinates = (previousCoordinates.equals(leftOfCurrentCoordinates)) ? bottomOfCurrentCoordinates : leftOfCurrentCoordinates;

            } else if (currentCharacter == 'F') {
                nextCoordinates = (previousCoordinates.equals(rightOfCurrentCoordinates)) ? bottomOfCurrentCoordinates : rightOfCurrentCoordinates;

            } else if (currentCharacter == '-') {
                nextCoordinates = (previousCoordinates.equals(leftOfCurrentCoordinates)) ? rightOfCurrentCoordinates : leftOfCurrentCoordinates;

            } else if (currentCharacter == 'L') {
                nextCoordinates = (previousCoordinates.equals(rightOfCurrentCoordinates)) ? topOfCurrentCoordinates : rightOfCurrentCoordinates;

            } else if (currentCharacter == 'J') {
                nextCoordinates = (previousCoordinates.equals(leftOfCurrentCoordinates)) ? topOfCurrentCoordinates : leftOfCurrentCoordinates;

            }
            previousCoordinates = currentCoordinates;
            currentCoordinates = nextCoordinates;
            stepsCounter++;
        }

        int farthestPoint = Math.divideExact(stepsCounter, 2);
        System.out.println(farthestPoint);

    }

    private static int[] findStartingPoint() {
        int[] startPoint = new int[2];

        for (int row = 0; row < inputArray.length; row++) {
            for (int column = 0; column < inputArray[row].length; column++) {
                char currentCharacter = inputArray[row][column];
                if (currentCharacter == 'S') {
                    startPoint[0] = row;
                    startPoint[1] = column;
                    break;
                }
            }
        }
        return startPoint;
    }

    private static char readCharacter(Coordinates coordinates) {
        return inputArray[coordinates.row()][coordinates.column()];
    }
}
