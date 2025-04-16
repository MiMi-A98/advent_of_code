package aoc2023.Day8.Part2;

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

            long result = getLCM();
            System.out.println(result);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static long getLCM() {
        List<Integer> stepsCountsUntilEndNodes = nodeMap.getStepsListToLastNodes(instructions);
        long lcm = stepsCountsUntilEndNodes.get(0).longValue();
        for (int i = 1; i < stepsCountsUntilEndNodes.size(); i++) {
            lcm = calcLowestCommonMultiple(lcm, stepsCountsUntilEndNodes.get(i).longValue());
        }

        return lcm;
    }


    public static long calcLowestCommonMultiple(long x, long y) {
        long max = Math.max(x, y);
        long min = Math.min(x, y);
        long lowestCommonMultiple = max;
        while (lowestCommonMultiple % min != 0) {
            lowestCommonMultiple += max;
        }
        return lowestCommonMultiple;
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
