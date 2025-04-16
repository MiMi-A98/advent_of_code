package aoc2023.Day2.Part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        try {
            FileReader fileReader = new FileReader("src/aoc2023.Day2/Input2.txt");
            BufferedReader reader = new BufferedReader(fileReader);

            int maxRedCubes = 12;
            int maxGreenCubes = 13;
            int maxBlueCubes = 14;

            String inputLine;
            ArrayList<Integer> validGameIds = new ArrayList<>();

            while ((inputLine = reader.readLine()) != null) {

                String[] gameSets = inputLine.split(";");
                boolean goodSet = true;

                for (String set : gameSets) {
                    Pattern numberPattern = Pattern.compile("(\\d+ blue|\\d+ red|\\d+ green)");
                    Matcher patternMatcher = numberPattern.matcher(set);

                    int blueCubes = 0;
                    int redCubes = 0;
                    int greenCubes = 0;

                    while (patternMatcher.find()) {
                        String foundMatch = patternMatcher.group();

                        String foundNumberStr = removeAllExceptDigits(foundMatch);
                        int foundNumber = Integer.parseInt(foundNumberStr);

                        if (foundMatch.contains("blue")) {
                            blueCubes = foundNumber;
                        } else if (foundMatch.contains("red")) {
                            redCubes = foundNumber;
                        } else if (foundMatch.contains("green")) {
                            greenCubes = foundNumber;
                        }
                    }

                    if (blueCubes > maxBlueCubes || redCubes > maxRedCubes || greenCubes > maxGreenCubes) {
                        goodSet = false;
                        break;
                    }
                }

                if (goodSet == true) {
                    String gameId = inputLine.substring(5, 8);
                    int id = Integer.parseInt(removeAllExceptDigits(gameId));
                    validGameIds.add(id);
                }
            }

            fileReader.close();
            System.out.println(validGameIds);
            System.out.println(getSum(validGameIds));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String removeAllExceptDigits(String input) {
        String result = input.replaceAll("[^0-9]", "");
        return result;
    }
    private static int getSum(ArrayList<Integer> numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }
}