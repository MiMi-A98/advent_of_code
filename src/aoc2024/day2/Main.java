package aoc2024.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try (FileReader fileReader = new FileReader("src/aoc2024/day2/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;
            int safeReports = 0;
            int safeModifiedReports = 0;

            while ((inputLine = reader.readLine()) != null) {
                String[] splitLine = inputLine.split("\\s");
                List<Integer> report = Arrays.stream(splitLine)
                        .mapToInt(Integer::parseInt).boxed().toList();

                if (isValidReport(report)) {
                    safeReports++;
                    safeModifiedReports++;
                } else {
                    List<Integer> modifiedReport = new ArrayList<>(report);
                    for (int i = 0; i < report.size(); i++) {
                        int removed = modifiedReport.remove(i);
                        if (isValidReport(modifiedReport)) {
                            safeModifiedReports++;
                            break;
                        }
                        modifiedReport.add(i, removed);
                    }
                }
            }

            System.out.println(safeReports + "\n" + safeModifiedReports);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean isValidReport(List<Integer> report) {
        boolean isIncreasing = report.get(0) < report.get(1);
        for (int i = 1; i < report.size(); i++) {
            int difference = Math.abs(report.get(i - 1) - report.get(i));
            if (difference < 1 || difference > 3 ||
                    (isIncreasing && report.get(i - 1) > report.get(i)) ||
                    (!isIncreasing && report.get(i - 1) < report.get(i))) {
                return false;
            }
        }
        return true;
    }
}


