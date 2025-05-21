package aoc2024.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {

    private record Coordinates(int row, int column) {
    }

    enum Directions {
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

    static int[][] input;

    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader("src/aoc2024/day10/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;
            List<int[]> inputLines = new ArrayList<>();

            while ((inputLine = reader.readLine()) != null) {
                String[] splitLine = inputLine.split("");
                int[] lineArray = Arrays.stream(splitLine).mapToInt(Integer::parseInt).toArray();
                inputLines.add(lineArray);
            }

            input = new int[inputLines.size()][];
            for (int i = 0; i < inputLines.size(); i++) {
                input[i] = inputLines.get(i);
            }

            List<Coordinates> summits = findSummits();

            Map<Coordinates, Integer> startPoints = findTrailsScores(summits);
            List<Integer> trailHeadsScores = startPoints.values().stream().toList();

            Map<Coordinates, Integer> ratings = findTrailsRateings();
            List<Integer> trailRatings = ratings.values().stream().toList();

            long sumPart1 = calculateSum(trailHeadsScores);
            long sumPart2 = calculateSum(trailRatings);

            System.out.println(sumPart1 + "\n" + sumPart2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<Coordinates> findSummits() {
        List<Coordinates> summits = new ArrayList<>();
        for (int row = 0; row < input.length; row++) {
            for (int column = 0; column < input[0].length; column++) {
                int current = input[row][column];
                if (current == 9) {
                    summits.add(new Coordinates(row, column));
                }
            }
        }
        return summits;
    }

    private static List<Coordinates> findTrailsHeads() {
        List<Coordinates> trailHeads = new ArrayList<>();
        for (int row = 0; row < input.length; row++) {
            for (int column = 0; column < input[0].length; column++) {
                int current = input[row][column];
                if (current == 0) {
                    trailHeads.add(new Coordinates(row, column));
                }
            }
        }
        return trailHeads;
    }

    private static Map<Coordinates, Integer> findTrailsScores(List<Coordinates> summits) {
        int rows = input.length;
        int columns = input[0].length;

        Map<Coordinates, Integer> startPoints = new HashMap<>();

        for (Coordinates summit : summits) {
            List<Coordinates> trail = new ArrayList<>();
            boolean[][] visited = new boolean[rows][columns];

            trail.add(summit);
            visited[summit.row()][summit.column()] = true;

            while (!trail.isEmpty()) {
                Coordinates current = trail.removeLast();
                int trailHeight = input[current.row()][current.column()];

                if (trailHeight == 0) {
                    startPoints.merge(current, 1, Integer::sum);
                    continue;
                }

                for (Directions direction : Directions.values()) {
                    int newRow = direction.directionRow + current.row();
                    int newColumn = direction.directionColumn + current.column();

                    if (newRow < 0 || newRow >= rows
                            || newColumn < 0 || newColumn >= columns)
                        continue;

                    if (visited[newRow][newColumn] || input[newRow][newColumn] != trailHeight - 1) continue;

                    visited[newRow][newColumn] = true;
                    trail.add(new Coordinates(newRow, newColumn));
                }
            }
        }
        return startPoints;
    }

    private static Map<Coordinates, Integer> findTrailsRateings() {
        int rows = input.length;
        int cols = input[0].length;

        long[][] distinctTrails = new long[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < cols; column++) {
                if (input[row][column] == 9) distinctTrails[row][column] = 1;
            }
        }

        for (int height = 8; height >= 0; height--) {
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < cols; column++) {
                    if (input[row][column] != height) continue;
                    long total = 0;

                    for (Directions direction : Directions.values()) {
                        int newRow = row + direction.directionRow;
                        int newColumn = column + direction.directionColumn;

                        if (newRow < 0 || newRow >= rows || newColumn < 0 || newColumn >= cols) continue;

                        if (input[newRow][newColumn] == height + 1) {
                            total += distinctTrails[newRow][newColumn];
                        }
                    }

                    distinctTrails[row][column] = total;
                }
            }
        }

        Map<Coordinates, Integer> ratings = new HashMap<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < cols; column++) {
                if (input[row][column] == 0) {
                    ratings.put(new Coordinates(row, column), (int) distinctTrails[row][column]);
                }
            }
        }
        return ratings;
    }

    private static long calculateSum(List<Integer> values) {
        long sum = 0;

        for (int value : values) {
            sum += value;
        }
        return sum;
    }
}

