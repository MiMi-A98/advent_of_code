package aoc2023.Day4.Part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static Map<Integer, Integer> cardCopies = new HashMap<>(); // also includes originals

    public static void main(String[] args) {

        String inputFileName = "src/aoc2023.Day4/Input2.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {

                String[] firstSplitOfLine = inputLine.split(":");
                String[] secondSplitOfLine = firstSplitOfLine[1].split("\\|");
                String[] cardId = firstSplitOfLine[0].split("\\s+");
                String[] winningNumbers = secondSplitOfLine[0].trim().split("\\s+");
                String[] cardNumbers = secondSplitOfLine[1].trim().split("\\s+");

                int currentCardNumber = Integer.parseInt(cardId[1]);

                if (!cardCopies.containsKey(currentCardNumber)) {
                    cardCopies.put(currentCardNumber, 1);
                }

                int matchCount = getMatchCount(winningNumbers, cardNumbers);

                createCardCopies(currentCardNumber, matchCount);
            }

            int sum = getSum(cardCopies);
            System.out.println(sum);

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private static int getMatchCount(String[] winningNumbers, String[] cardNumbers) {
        int matchCount = 0;
        for (String winNumber : winningNumbers) {
            for (String cardNumber : cardNumbers) {
                if (winNumber.equals(cardNumber)) {
                    matchCount += 1;
                }
            }
        }
        return matchCount;
    }

    private static void createCardCopies(int currentCardNumber, int matchCount) {

        for (int currentCardCopy = 1; currentCardCopy <= cardCopies.get(currentCardNumber); currentCardCopy++) {
            for (int copyWonCount = 1; copyWonCount <= matchCount; copyWonCount++) {
                int nextCardNumber = currentCardNumber + copyWonCount;

                if (cardCopies.containsKey(nextCardNumber)) {
                    cardCopies.put(nextCardNumber, cardCopies.get(nextCardNumber) + 1);
                } else {
                    cardCopies.put(nextCardNumber, 2); //initialize original + first copy
                }
            }
        }
    }

    private static int getSum(Map<Integer, Integer> cardCopies) {
        int sum = 0;
        for (int i = 1; i <= cardCopies.size(); i++) {
            sum += cardCopies.get(i);
        }
        return sum;
    }

}
