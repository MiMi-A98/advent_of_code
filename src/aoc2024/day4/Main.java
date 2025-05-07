package aoc2024.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

enum DirectionsPart1 {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    ULEFT(-1, -1),
    URIGHT(-1, 1),
    DLEFT(1, -1),
    DRIGHT(1, 1);

    final int directionRow;
    final int directionColumn;

    DirectionsPart1(int directionRow, int directionColumn) {
        this.directionRow = directionRow;
        this.directionColumn = directionColumn;
    }
}

enum DirectionsPart2 {
    UPLEFT(-1, -1),
    UPRIGHT(-1, 1),
    DOWNLEFT(1, -1),
    DOWNRIGHT(1, 1);

    final int directionRow;
    final int directionColumn;

    DirectionsPart2(int directionRow, int directionColumn) {
        this.directionRow = directionRow;
        this.directionColumn = directionColumn;
    }
}

public class Main {
    public static void main(String[] args) {

        try (FileReader fileReader = new FileReader("src/aoc2024/day4/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            List<char[]> rowsList = new ArrayList<>();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                char[] lineAsArray = inputLine.toCharArray();
                rowsList.add(lineAsArray);
            }

            char[][] inputArray = new char[rowsList.size()][];

            for (int i = 0; i < rowsList.size(); i++) {
                inputArray[i] = rowsList.get(i);
            }

            int counterPart1 = xmasCount(inputArray);
            int counterPart2 = masCount(inputArray);

            System.out.println(counterPart1 + "\n" + counterPart2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static int xmasCount(char[][] inputArray) {
        char[] target = {'X', 'M', 'A', 'S'};
        int counter = 0;

        for (int row = 0; row < inputArray.length; row++) {
            for (int column = 0; column < inputArray[row].length; column++) {
                char currentCharacter = inputArray[row][column];
                if (currentCharacter == target[0]) {
                    for (DirectionsPart1 direction : DirectionsPart1.values()) {
                        int directionRow = direction.directionRow;
                        int directionColumn = direction.directionColumn;

                        int rowTarget1 = row + directionRow, columnTarget1 = column + directionColumn;
                        int rowTarget2 = row + 2 * directionRow, columnTarget2 = column + 2 * directionColumn;
                        int rowTarget3 = row + 3 * directionRow, columnTarget3 = column + 3 * directionColumn;

                        if (rowTarget3 < 0 || rowTarget3 > inputArray.length - 1
                                || columnTarget3 < 0 || columnTarget3 > inputArray[row].length - 1)
                            continue;
                        if (inputArray[rowTarget1][columnTarget1] == target[1] &&
                                inputArray[rowTarget2][columnTarget2] == target[2] &&
                                inputArray[rowTarget3][columnTarget3] == target[3]) {
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }

    private static int masCount(char[][] inputArray) {
        char[] target = {'M', 'A', 'S'};
        int counter = 0;

        DirectionsPart2[][] diagonals = {
                {DirectionsPart2.UPLEFT, DirectionsPart2.DOWNRIGHT},
                {DirectionsPart2.UPRIGHT, DirectionsPart2.DOWNLEFT}
        };

        for (int row = 0; row < inputArray.length; row++) {
            for (int column = 0; column < inputArray[row].length; column++) {
                char currentCharacter = inputArray[row][column];
                if (currentCharacter == target[1]) {
                    boolean bothMatch = true;

                    for (DirectionsPart2[] diagonal : diagonals) {
                        int rowTarget1 = row + diagonal[0].directionRow, columnTarget1 = column + diagonal[0].directionColumn;
                        int rowTarget2 = row + diagonal[1].directionRow, columnTarget2 = column + diagonal[1].directionColumn;

                        if (rowTarget1 < 0 || rowTarget1 > inputArray.length - 1
                                || columnTarget1 < 0 || columnTarget1 > inputArray[row].length - 1
                                || rowTarget2 < 0 || rowTarget2 > inputArray.length - 1
                                || columnTarget2 < 0 || columnTarget2 > inputArray[row].length - 1) {
                            bothMatch = false;
                            break;
                        }

                        char character1 = inputArray[rowTarget1][columnTarget1];
                        char character2 = inputArray[rowTarget2][columnTarget2];

                        boolean diagonalMatch = (character1 == target[0] && character2 == target[2])
                                || (character1 == target[2] && character2 == target[0]);

                        if (!diagonalMatch) {
                            bothMatch = false;
                            break;
                        }
                    }

                    if (bothMatch) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }
}
