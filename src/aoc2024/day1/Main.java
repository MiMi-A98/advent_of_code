package aoc2024.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try (FileReader fileReader = new FileReader("src/aoc2024/day1/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;
            List<Integer> leftList = new ArrayList<>();
            List<Integer> rightList = new ArrayList<>();

            while ((inputLine = reader.readLine()) != null) {
                String[] splitLine = inputLine.split("\\s+");
                int leftValue = Integer.parseInt(splitLine[0]);
                int rightValue = Integer.parseInt(splitLine[1]);
                leftList.add(leftValue);
                rightList.add(rightValue);
            }

            leftList.sort(null);
            rightList.sort(null);

            int sum = calculateTotalDifference(leftList, rightList);
            System.out.println(sum);

            int totalSimilarityScore = calculateSimilarityScore(leftList, rightList);
            System.out.println(totalSimilarityScore);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int calculateTotalDifference(List<Integer> leftList, List<Integer> rightList) {
        int sum = 0;
        for (int i = 0; i < leftList.size(); i++) {
            int leftValue = leftList.get(i);
            int rightValue = rightList.get(i);
            int difference;

            if (leftValue < rightValue) {
                difference = rightValue - leftValue;
            } else {
                difference = leftValue - rightValue;
            }
            sum += difference;
        }
        return sum;
    }

    public static int calculateSimilarityScore(List<Integer> leftList, List<Integer> rightList) {
        int sum = 0;
        for (int leftElem : leftList) {
            int count = 0;
            for (int rightElem : rightList) {
                if (leftElem == rightElem) {
                    count++;
                } else if (rightElem > leftElem) {
                    break;
                }
            }
            int similarityScore = leftElem * count;
            sum += similarityScore;
        }
        return sum;
    }
}
