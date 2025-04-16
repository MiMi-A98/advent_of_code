package aoc2023.Day7.Part1;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    static Hands handsList = new Hands();

    public static void main(String[] args) {

        String inputFileName = "src/aoc2023.Day7/Input2.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;

            while ((inputLine = reader.readLine()) != null) {

                String[] game = inputLine.split("\\s");
                String hand = game[0];
                int bid = Integer.parseInt(game[1]);

                handsList.addHand(hand, bid);
            }

            handsList.sortHandsOnType();
            handsList.orderAllLists();

            long sumResult = handsList.sumMultiplyResults(handsList.multiply(handsList.combineLists()));

            System.out.println(sumResult);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
