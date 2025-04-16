package aoc2023.Day15.Part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String inputFileName = "src/aoc2023.Day15/Input.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                String[] lineSplit = inputLine.split("\\s");
                List<String>  initializationSequence = new ArrayList<>();
                for (String elem : lineSplit) {

                }

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
