package aoc2024.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        try (FileReader fileReader = new FileReader("src/aoc2024/day3/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            List<String> foundMatchesPartOne = new ArrayList<>();
            List<String> foundMatchesPartTwo = new ArrayList<>();

            String inputLine;
            boolean takeMatch = true;

            while ((inputLine = reader.readLine()) != null) {

                Pattern firstPartPattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
                Matcher firstMatcher = firstPartPattern.matcher(inputLine);

                while (firstMatcher.find()) {
                        String match = firstMatcher.group();
                        foundMatchesPartOne.add(match);
                }

                Pattern secondPattern = Pattern.compile("(?<MUL>mul\\(\\d{1,3},\\d{1,3}\\))" +
                                                            "|(?<OFF>don't\\(\\))" +
                                                            "|(?<ON>do\\(\\))");
                Matcher secondMatcher = secondPattern.matcher(inputLine);

                while (secondMatcher.find()) {
                    if (secondMatcher.group("MUL") != null && takeMatch) {
                        String match = secondMatcher.group("MUL");
                        foundMatchesPartTwo.add(match);
                    } else if (secondMatcher.group("OFF") != null) {
                        takeMatch = false;
                    } else if (secondMatcher.group("ON") != null) {
                        takeMatch = true;
                    }

                }
            }

            int sumPartOne = calculateSum(getMultiplicationValues(foundMatchesPartOne));
            int sumPartTwo = calculateSum(getMultiplicationValues(foundMatchesPartTwo));

            System.out.println(sumPartOne + "\n" + sumPartTwo);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Integer> getMultiplicationValues(List<String> foundMatches) {
        List<Integer> multiplicationValues = new ArrayList<>();
        for (String match : foundMatches) {
            Pattern pattern = Pattern.compile("\\d{1,3}");
            Matcher matcher = pattern.matcher(match);

            while (matcher.find()) {
                int matchedValue = Integer.parseInt(matcher.group());
                multiplicationValues.add(matchedValue);
            }
        }
        return multiplicationValues;
    }

    public static int calculateSum(List<Integer> multiplicationValues) {
        int sum = 0;

        for (int i = 0; i < multiplicationValues.size() - 1; i += 2) {
            int a = multiplicationValues.get(i);
            int b = multiplicationValues.get(i + 1);
            int multiplicationResult = Math.multiplyExact(a, b);
            sum += multiplicationResult;
        }

        return sum;
    }
}
