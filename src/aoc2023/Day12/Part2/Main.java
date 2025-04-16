package aoc2023.Day12.Part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String inputFileName = "src/aoc2023.Day12/Input2.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;
            String springRow = null;
            List<Integer> groups = null;
            long arrangementsSum = 0;


            while ((inputLine = reader.readLine()) != null) {
                String[] lineSplit = inputLine.split("\\s");
                springRow = lineSplit[0];
                groups = Arrays.stream(lineSplit[1].split(",")).map(Integer::valueOf).toList();
                SpringConditionRecord springCondition = new SpringConditionRecord(springRow, groups);
                springCondition.unfoldRecord(5);
                arrangementsSum += springCondition.countArrangements();
            }

            System.out.println(arrangementsSum);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
