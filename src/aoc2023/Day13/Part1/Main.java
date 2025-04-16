package aoc2023.Day13.Part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Notes> patterns = new ArrayList<>();

    public static void main(String[] args) {

        String inputFileName = "src/aoc2023.Day13/Input2.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;
            List<String> list = new ArrayList<>();

            while ((inputLine = reader.readLine()) != null) {
                if (inputLine.isBlank()) {
                    patterns.add(new Notes(list));
                    list = new ArrayList<>();
                } else {
                    list.add(inputLine);
                }
            }
            patterns.add(new Notes(list));

            for (Notes notes : patterns) {
                notes.findVerticalMirrors();
                notes.findHorizontalMirrors();
            }

            int sum = Notes.calculateMirrorsSum();
            System.out.println(sum);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
