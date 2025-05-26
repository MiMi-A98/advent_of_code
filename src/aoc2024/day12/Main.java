package aoc2024.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {

    private record Coordinates(int row, int column) {
    }

    private enum Directions {
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1);

        final int directionRow;
        final int directionColumn;

        Directions(int directionRow, int directionColumn) {
            this.directionRow = directionRow;
            this.directionColumn = directionColumn;
        }
    }

    private static char[][] garden;
    private static Set<Coordinates> visited;

    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader("src/aoc2024/day12/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;
            List<char[]> gardenLines = new ArrayList<>();

            while ((inputLine = reader.readLine()) != null) {
                char[] line = inputLine.toCharArray();
                gardenLines.add(line);
            }

            garden = new char[gardenLines.size()][];
            for (int i = 0; i < gardenLines.size(); i++) {
                garden[i] = gardenLines.get(i);
            }

            int fenceTotalCost = calculateTotalFenceCost();
            int fenceTotalDiscountedCost = calculateDiscountedFenceCost();

            System.out.println(fenceTotalCost + "\n" + fenceTotalDiscountedCost);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static int calculateTotalFenceCost() {
        visited = new HashSet<>();
        int fenceTotalCost = 0;

        for (int row = 0; row < garden.length; row++) {
            for (int column = 0; column < garden[0].length; column++) {
                Coordinates coordinates = new Coordinates(row, column);
                if (!visited.contains(coordinates)) {
                    fenceTotalCost += findRegionFenceCost(coordinates, false);
                }
            }
        }
        return fenceTotalCost;
    }

    private static int calculateDiscountedFenceCost() {
        visited = new HashSet<>();
        int fenceTotalCost = 0;

        for (int row = 0; row < garden.length; row++) {
            for (int column = 0; column < garden[0].length; column++) {
                Coordinates coordinates = new Coordinates(row, column);
                if (!visited.contains(coordinates)) {
                    fenceTotalCost += findRegionFenceCost(coordinates, true);
                }
            }
        }
        return fenceTotalCost;
    }

    private static int findRegionFenceCost(Coordinates coordinates, boolean discount) {
        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(coordinates);
        visited.add(coordinates);

        char currentChar = garden[coordinates.row()][coordinates.column()];
        int area = 0;
        int perimeter = 0;

        Set<Coordinates> region = new HashSet<>();

        while (!queue.isEmpty()) {
            Coordinates currentCoord = queue.poll();
            region.add(currentCoord);
            ++area;

            int row = currentCoord.row();
            int column = currentCoord.column();

            for (Directions direction : Directions.values()) {
                int newRow = direction.directionRow + row;
                int newColumn = direction.directionColumn + column;
                Coordinates newCoord = new Coordinates(newRow, newColumn);
                if (newRow < 0 || newRow >= garden.length
                        || newColumn < 0 || newColumn >= garden[0].length) {
                    ++perimeter;
                } else if (garden[newRow][newColumn] != currentChar) {
                    ++perimeter;

                } else if (!visited.contains(newCoord)) {
                    queue.add(newCoord);
                    visited.add(newCoord);
                }
            }
        }

        if (discount) {
            return area * getFenceSides(region);
        } else {
            return area * perimeter;
        }
    }

    private static int getFenceSides(Set<Coordinates> region) {
        boolean[][] foundAbove = new boolean[garden.length][garden.length];
        boolean[][] foundBelow = new boolean[garden.length][garden.length];
        boolean[][] foundLeft = new boolean[garden.length][garden.length];
        boolean[][] foundRight = new boolean[garden.length][garden.length];

        for (int row = 0; row < garden.length; row++) {
            for (int column = 0; column < garden.length; column++) {
                if (region.contains(new Coordinates(row, column))) {

                    if (row == 0 || !region.contains(new Coordinates(row - 1, column)))
                        foundAbove[row][column] = true;

                    if (row == garden.length - 1 || !region.contains(new Coordinates(row + 1, column)))
                        foundBelow[row][column] = true;

                    if (column == 0 || !region.contains(new Coordinates(row, column - 1)))
                        foundLeft[row][column] = true;

                    if (column == garden.length - 1 || !region.contains(new Coordinates(row, column + 1)))
                        foundRight[row][column] = true;

                }
            }
        }
        return countSides(foundAbove, false)
                + countSides(foundBelow, false)
                + countSides(foundLeft, true)
                + countSides(foundRight, true);
    }

    private static int countSides(boolean[][] grid, boolean countVertically) {
        int count = 0;
        boolean foundFence = false;

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                boolean hasFence = countVertically ? grid[column][row] : grid[row][column];

                if (hasFence && !foundFence) {
                    count++;
                    foundFence = true;
                }

                if (!hasFence) {
                    foundFence = false;
                }
            }
        }
        return count;
    }

}
