package aoc2024.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Part1 {
    public static void main(String[] args) {

        try (FileReader fileReader = new FileReader("src/aoc2024/day2/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;
            int safeReportsCounter = 0;

            while ((inputLine = reader.readLine()) != null) {
                String[] splitLine = inputLine.split("\\s");
                int[] report = Arrays.stream(splitLine)
                        .mapToInt(Integer::parseInt)
                        .toArray();

                boolean isIncreasing = false;
                boolean isDecresing = false;
                for (int i = 1; i < report.length; i++) {
                    if (report[0] < report[i]) {
                        isIncreasing = true;
                    } else if (report[0] > report[i]) {
                        isDecresing = true;
                    }
                }

                boolean isValidReport = true;
                if (isIncreasing == isDecresing) {
                    isValidReport = false;
                } else {
                    if (isIncreasing) {
                        for (int i = report.length - 1; i >= 1; i--) {
                            int difference = report[i] - report[i - 1];
                            if (difference < 1 || difference > 3) {
                                isValidReport = false;
                                break;
                            }
                        }
                    } else if (isDecresing) {
                        for (int i = 0; i < report.length - 1; i++) {
                            int difference = report[i] - report[i + 1];
                            if (difference < 1 || difference > 3) {
                                isValidReport = false;
                                break;
                            }
                        }

                    }
                }

                if (isValidReport) {
                    safeReportsCounter++;
                }
            }

            System.out.println(safeReportsCounter);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
