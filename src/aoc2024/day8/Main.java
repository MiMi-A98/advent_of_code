package aoc2024.day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class Main {

    private record Coordinates(int row, int column) {
    }

    static char[][] inputArray;
    static Set<Coordinates> antinodes;
    private static HashMap<Character, ArrayList<Coordinates>> antennas = new HashMap<>();

    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader("src/aoc2024/day8/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            List<char[]> rowsList = new ArrayList<>();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                char[] lineAsArray = inputLine.toCharArray();
                rowsList.add(lineAsArray);
            }

            inputArray = new char[rowsList.size()][];

            for (int i = 0; i < rowsList.size(); i++) {
                inputArray[i] = rowsList.get(i);
            }

            populateAntennaPositions();
            antinodes = new HashSet<>(calculateAntinodePositions(false));
            System.out.println(antinodes.size());
            antinodes = new HashSet<>(calculateAntinodePositions(true));
            System.out.println(antinodes.size());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static Set<Coordinates> calculateAntinodePositions(boolean resonateHarmonics) {
        Set<Coordinates> antinodes = new HashSet<>();

        for (ArrayList<Coordinates> value : antennas.values()) {
            if (value.size() > 1) {
                for (int i = 0; i < value.size(); i++) {
                    int y = value.get(i).row;
                    int x = value.get(i).column;
                    for (int j = i + 1; j < value.size(); j++) {
                        int compareY = value.get(j).row;
                        int compareX = value.get(j).column;

                        int yDiff = y - compareY;
                        int xDiff = x - compareX;

                        int antinode1Y = y + yDiff;
                        int antinode1X = x + xDiff;

                        int antinode2Y = compareY - yDiff;
                        int antinode2X = compareX - xDiff;

                        while (antinode1Y < inputArray.length && antinode1Y >= 0 && antinode1X < inputArray[y].length && antinode1X >= 0) {
                            antinodes.add(new Coordinates(antinode1Y, antinode1X));

                            if (!resonateHarmonics) break;

                            antinode1Y += yDiff;
                            antinode1X += xDiff;
                        }

                        while (antinode2Y < inputArray.length && antinode2Y >= 0 && antinode2X < inputArray[y].length && antinode2X >= 0) {
                            antinodes.add(new Coordinates(antinode2Y, antinode2X));

                            if (!resonateHarmonics) break;

                            antinode2Y -= yDiff;
                            antinode2X -= xDiff;
                        }

                        if (resonateHarmonics) {
                            antinodes.add(new Coordinates(y, x));
                            antinodes.add(new Coordinates(compareY, compareX));
                        }
                    }
                }
            }
        }

        return antinodes;
    }

    private static void populateAntennaPositions() {
        for (int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < inputArray[i].length; j++) {
                if (inputArray[i][j] != '.') {
                    ArrayList<Coordinates> coords = antennas.getOrDefault(inputArray[i][j], new ArrayList<>());
                    coords.add(new Coordinates(i, j));
                    antennas.put(inputArray[i][j], coords);
                }
            }
        }
    }

}
