package aoc2023.Day1.Part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        try {
            FileReader fileReader = new FileReader("src/aoc2023.Day1/Input2.txt");
            BufferedReader reader = new BufferedReader(fileReader);

            ArrayList<Integer> calibrationValues = new ArrayList<>();

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                ArrayList<Character> lineDigits = new ArrayList<>();

                Pattern digitPattern = Pattern.compile("(one|two|three|four|five|six|seven|eight|nine|1|2|3|4|5|6|7|8|9)");
                Matcher patternMatcher = digitPattern.matcher(inputLine);

                patternMatcher.find();
                do {
                    String foundDigit = patternMatcher.group();
                    switch (foundDigit) {
                        case "one":
                            lineDigits.add('1');
                            break;
                        case "two":
                            lineDigits.add('2');
                            break;
                        case "three":
                            lineDigits.add('3');
                            break;
                        case "four":
                            lineDigits.add('4');
                            break;
                        case "five":
                            lineDigits.add('5');
                            break;
                        case "six":
                            lineDigits.add('6');
                            break;
                        case "seven":
                            lineDigits.add('7');
                            break;
                        case "eight":
                            lineDigits.add('8');
                            break;
                        case "nine":
                            lineDigits.add('9');
                            break;
                        case "1", "2", "3", "4", "5", "6", "7", "8", "9":
                            lineDigits.add(foundDigit.charAt(0));
                    }
                } while (patternMatcher.find(patternMatcher.start() + 1));

                char firstDigit =  lineDigits.get(0);
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