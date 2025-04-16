package aoc2023.Day2.Part2;

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


            String inputLine;
            ArrayList<Integer> multiplicationResults = new ArrayList<>();

            while ((inputLine = reader.readLine()) != null) {

                ArrayList<Integer> allBlueCubes = new ArrayList<>();
                ArrayList<Integer> allRedCubes = new ArrayList<>();
                ArrayList<Integer> allGreenCubes = new ArrayList<>();
                ArrayList<Integer> maxCubeNumbers = new ArrayList<>();

                Pattern numberPattern = Pattern.compile("(\\d+ blue|\\d+ red|\\d+ green)");
                Matcher patternMatcher = numberPattern.matcher(inputLine);

                while (patternMatcher.find()) {
                    String foundMatch = patternMatcher.group();

                    String foundNumberStr = removeAllExceptDigits(foundMatch);
                    int foundNumber = Integer.parseInt(foundNumberStr);

                    if (foundMatch.contains("blue")) {
                        allBlueCubes.add(foundNumber);
                    } else if (foundMatch.contains("red")) {
                        allRedCubes.add(foundNumber);
                    } else if (foundMatch.contains("green")) {
                        allGreenCubes.add(foundNumber);
                    }
                }

                maxCubeNumbers.add(findMaxElement(allBlueCubes));
                maxCubeNumbers.add(findMaxElement(allRedCubes));
                maxCubeNumbers.add(findMaxElement(allGreenCubes));

                multiplicationResults.add(multiplyValues(maxCubeNumbers));
            }

            fileReader.close();
            System.out.println(getSum(multiplicationResults));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int findMaxElement(ArrayList<Integer> inputList) {
        int maxValue = inputList.get(0);
            for (int element : inputList) {
                if (element > maxValue) {
                    maxValue = element;
                }
            }
        return maxValue;
    }

    private static int multiplyValues(ArrayList<Integer> inputList) {
        int multiplyResult = 1;
        for (int element : inputList) {
            multiplyResult *= element;
        }
        return multiplyResult;
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