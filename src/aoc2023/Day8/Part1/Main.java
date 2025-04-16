package aoc2023.Day8.Part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Character> instructions = new ArrayList<>();
    static Maps nodeMap = new Maps();

    public static void main(String[] args) {
        String inputFileName = "src/aoc2023.Day8/Input2.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;


            while ((inputLine = reader.readLine()) != null) {

                if (inputLine.contains("=")) {
                    String[] lineSplit = inputLine.split("=");
                    String[] lineDestinations = lineSplit[1].replace('(', ' ').replace(')', ' ').split(",");
                    String[] destinations = new String[2];
                    String node = lineSplit[0].trim();
                    destinations[0] = lineDestinations[0].trim();
                    destinations[1] = lineDestinations[1].trim();
                    nodeMap.addMap(node, destinations[0], destinations[1]);

                } else {

                    char[] lineCharacters = inputLine.toCharArray();
                    for (char character : lineCharacters) {
                        instructions.add(character);
                    }

                }
            }

           int stepsCount = nodeMap.getStepsNumbToLastNode(instructions);
            System.out.println(stepsCount);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public static int getDirectionValue(char character) {

        switch (character) {
            case 'L':
                return 0;
            case 'R':
                return 1;
        }

        return 2;
    }

}
