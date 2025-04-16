package aoc2023.Day1.Part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        try {
            FileReader fileReader = new FileReader("src/aoc2023.Day1/Input2.txt");
            BufferedReader reader = new BufferedReader(fileReader);

            String inputLine;
            ArrayList<Integer> calibrationValues = new ArrayList<>();

            while ((inputLine = reader.readLine()) != null) {
                char[] lineCharacters = inputLine.toCharArray();
                ArrayList<Character> lineDigits = new ArrayList<>();

                for (char character : lineCharacters) {
                    if (Character.isDigit(character) == true) {
                        lineDigits.add(character);
                    }
                }
                char firstDigit = lineDigits.get(0);
                char secondDigit = lineDigits.get(lineDigits.size() - 1);
                String firstDigitStr = Character.toString(firstDigit);
                String secondDigitStr = Character.toString(secondDigit);
                int number = Integer.parseInt(firstDigitStr.concat(secondDigitStr));

                calibrationValues.add(number);
            }
            fileReader.close();

            int calibrationSum = 0;
            for (int value : calibrationValues) {
                calibrationSum += value;
            }

            System.out.println(calibrationSum);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}