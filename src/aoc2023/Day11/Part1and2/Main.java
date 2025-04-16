package aoc2023.Day11.Part1and2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static Map<Integer, Coordinates> galaxyCoordinates = new HashMap<>();
    static List<String> input = new ArrayList<>();

    public static void main(String[] args) {

        String inputFileName = "src/aoc2023.Day11/Input2.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                input.add(inputLine);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Integer> rowsToExpand = getRowsToExpand();
        List<Integer> columnsToExpand = getColumnsToExpand();
        determineGalaxyCoordinates();

        long distancePart1 = calculateDistance(rowsToExpand, columnsToExpand, 2);
        long distancePart2 = calculateDistance(rowsToExpand, columnsToExpand, 1000000);

        System.out.println(distancePart1);
        System.out.println(distancePart2);
    }
    private static List<Integer> getRowsToExpand() {
        List<Integer> rowsToExpand = new ArrayList<>();
        for (int row = 0; row < input.size(); row++) {
            String inputRow = input.get(row);
            if (inputRow.chars().allMatch(character -> character == '.')) {
                rowsToExpand.add(row);
            }
        }
        return rowsToExpand;
    }

    private static List<Integer> getColumnsToExpand() {
        char[][] galaxies = convertGalaxyInputTo2DArray();

        List<Integer> columnsToExpand = new ArrayList<>();
        for (int column = 0; column < galaxies[0].length; column++) {
            boolean foundGalaxy = false;
            for (int row = 0; row < galaxies.length; row++) {
                char currentColumn = galaxies[row][column];
                if (currentColumn == '#') {
                    foundGalaxy = true;
                    break;
                }
            }
            if (foundGalaxy == false) {
                columnsToExpand.add(column);
            }
        }
        return columnsToExpand;
    }

    private static void determineGalaxyCoordinates() {
        char[][] galaxies = convertGalaxyInputTo2DArray();
        int galaxyId = 1;
        for (int row = 0; row < galaxies.length; row++) {
            for (int column = 0; column < galaxies[row].length; column++) {
                char currentCharacter = galaxies[row][column];
                if (currentCharacter == '#') {
                    galaxyCoordinates.put(galaxyId, new Coordinates(row, column));
                    galaxyId++;
                }
            }
        }
    }

    private static char[][] convertGalaxyInputTo2DArray() {
        char[][] galaxies = new char[input.size()][];
        for (int i = 0; i < input.size(); i++) {
            galaxies[i] = input.get(i).toCharArray();
        }
        return galaxies;
    }

    private static long calculateDistance(List<Integer> rowsToExpand, List<Integer> columnsToExpand, int timesToExpand) {
        long sum = 0;
        for (int i = 1; i < galaxyCoordinates.size(); i++) {
            for (int j = i + 1; j <= galaxyCoordinates.size(); j++) {
                Coordinates firstCoordinates = galaxyCoordinates.get(i);
                Coordinates secondCoordinates = galaxyCoordinates.get(j);
                int rowDifference = firstCoordinates.row() - secondCoordinates.row();
                int columnDifference = firstCoordinates.column() - secondCoordinates.column();
                int distance = Math.abs(rowDifference) + Math.abs(columnDifference)
                        + calculateTimesToExpand(firstCoordinates.row(), secondCoordinates.row(), rowsToExpand, timesToExpand)
                        + calculateTimesToExpand(firstCoordinates.column(), secondCoordinates.column(), columnsToExpand, timesToExpand);
                sum += distance;
            }
        }

        return sum;
    }

    private static int calculateTimesToExpand(int first, int second, List<Integer> toAdd, int timesToExpand) {
        int total = 0;
        int start = Math.min(first, second);
        int end = Math.max(first, second);

        for (int i = start; i < end; i++) {
            if (toAdd.contains(i)) {
                total++;
            }
        }
        return total * (timesToExpand - 1);
    }

}
