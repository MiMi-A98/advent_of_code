package aoc2023.Day14.Part1;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    static char[][] inputArray;

    public static void main(String[] args) {

        String inputFileName = "src/aoc2023.Day14/Input2.txt";

        try {
            // read file first time to determine the number of lines it has, to initialize the 2D array
            FileReader fileReader = new FileReader(inputFileName);
            BufferedReader reader = new BufferedReader(fileReader);

            int inputLineSize = (int) reader.lines().count();
            inputArray = new char[inputLineSize][];

            // read file again to store the values in the 2D array
            fileReader = new FileReader(inputFileName);
            reader = new BufferedReader(fileReader);

            String inputLine;
            int i = 0;
            while ((inputLine = reader.readLine()) != null) {
                inputArray[i] = inputLine.toCharArray();
                i++;
            }
            fileReader.close();

            Field turnedField = new Field(inputArray);
            char[][] field = turnedField.turnNorth();
            long beamsLoad = turnedField.calculateBeamsLoad(field);
            System.out.println(beamsLoad);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
