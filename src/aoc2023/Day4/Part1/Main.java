package aoc2023.Day4.Part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    static ArrayList<Integer> cardPoints = new ArrayList<>();

    public static void main(String[] args) {

        String inputFileName = "src/aoc2023.Day4/Input2.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;

            while ((inputLine = reader.readLine()) != null) {

                String[] firstSplit = inputLine.split(":");
                String[] secondSplit = firstSplit[1].split("\\|");
                String[] winningNumbers = secondSplit[0].trim().split("\\s+");
                String[] cardNumbers = secondSplit[1].trim().split("\\s+");

                int matchCount = 0;

                for (String winNumber : winningNumbers) {
                    for (String cardNumber : cardNumbers) {
                        if (winNumber.equals(cardNumber)) {
                            matchCount += 1;

                        }
                    }
                }

                int points = getCardPoints(matchCount);
                cardPoints.add(points);
            }

            System.out.println(getSum(cardPoints));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int getCardPoints(int matchCount) {

        int points = (int) Math.pow(2, matchCount - 1.0);
        return points;


    }

    private static int getSum(ArrayList<Integer> numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

}
