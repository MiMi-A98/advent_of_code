package aoc2023.Day7.Part1;

import java.util.*;

public class Hands {

    private record Hand(String gameHand, int bid) {
    }

    private List<Hand> handsList;
    private List<Hand> fiveOfAKind = new ArrayList<>();
    private List<Hand> fourOfAKind = new ArrayList<>();
    private List<Hand> fullHouse = new ArrayList<>();
    private List<Hand> threeOfAKind = new ArrayList<>();
    private List<Hand> twoPair = new ArrayList<>();
    private List<Hand> onePair = new ArrayList<>();
    private List<Hand> highCard = new ArrayList<>();

    public Hands() {
        handsList = new ArrayList<>();
    }

    public void addHand(String gameHand, int bid) {
        Hand hand = new Hand(gameHand, bid);
        handsList.add(hand);
    }

    @Override
    public String toString() {
        return "{" + "Five of a kind" + fiveOfAKind + "}" + "\n" +
                "{" + "Four of a kind" + fourOfAKind + "}" + "\n" +
                "{" + "Full House" + fullHouse + "}" + "\n" +
                "{" + "Three of a kind" + threeOfAKind + "}" + "\n" +
                "{" + "Two Pair" + twoPair + "}" + "\n" +
                "{" + "One Pair" + onePair + "}" + "\n" +
                "{" + "High Card" + highCard + "}";
    }

    public void sortHandsOnType() {

        for (Hand hand : handsList) {
            Map<Character, Integer> charactersCount = new HashMap<>();

            for (char character : hand.gameHand.toCharArray()) {
                if (charactersCount.containsKey(character)) {
                    charactersCount.put(character, charactersCount.get(character) + 1);
                } else {
                    charactersCount.putIfAbsent(character, 1);
                }
            }

            if (charactersCount.size() == 5) {
                highCard.add(hand);

            } else if (charactersCount.size() == 4) {
                onePair.add(hand);

            } else if (charactersCount.size() == 3) {
                if (charactersCount.containsValue(2)) {
                    twoPair.add(hand);
                } else {
                    threeOfAKind.add(hand);
                }

            } else if (charactersCount.size() == 2) {
                if (charactersCount.containsValue(2)) {
                    fullHouse.add(hand);
                } else {
                    fourOfAKind.add(hand);
                }

            } else if (charactersCount.size() == 1) {
                fiveOfAKind.add(hand);
            }
        }
    }

    private int getCardValue(char c) {

        if (Character.isDigit(c)) {
            return Character.getNumericValue(c);
        } else {
            switch (c) {
                case 'A':
                    return 14;
                case 'K':
                    return 13;
                case 'Q':
                    return 12;
                case 'J':
                    return 11;
                case 'T':
                    return 10;
            }
        }
        return 0;
    }

    private List<Hand> orderList(List<Hand> arrayList) {

        List<Hand> orderedList = new ArrayList<>(arrayList);

        if (orderedList.size() > 1) {
            for (int firstHandIndex = 0; firstHandIndex < orderedList.size() - 1; firstHandIndex++) {
                Hand smallerHand = orderedList.get(firstHandIndex);

                for (int secondHandIndex = firstHandIndex + 1; secondHandIndex <= orderedList.size() - 1; secondHandIndex++) {
                    Hand secondHand = orderedList.get(secondHandIndex);

                    //compare hands
                    for (int i = 0; i < 5; i++) {
                        int firstHandCardValue = getCardValue(smallerHand.gameHand.toCharArray()[i]);
                        int secondHandCardValue = getCardValue(secondHand.gameHand.toCharArray()[i]);

                        if (firstHandCardValue > secondHandCardValue) {
                            smallerHand = orderedList.get(secondHandIndex);
                            break;
                        } else if (firstHandCardValue < secondHandCardValue) {
                            break;
                        }
                    }
                }
                Hand tempHand = orderedList.get(firstHandIndex);
                orderedList.set(firstHandIndex, smallerHand);
                orderedList.set(orderedList.lastIndexOf(smallerHand), tempHand);
            }
        }
        return orderedList;
    }

    public void orderAllLists() {
        fiveOfAKind = orderList(fiveOfAKind);
        fourOfAKind = orderList(fourOfAKind);
        fullHouse = orderList(fullHouse);
        threeOfAKind = orderList(threeOfAKind);
        twoPair = orderList(twoPair);
        onePair = orderList(onePair);
        highCard = orderList(highCard);
    }

    public ArrayList<Hand> combineLists() {
        ArrayList<Hand> finalOrderedList = new ArrayList<>();

        finalOrderedList.addAll(highCard);
        finalOrderedList.addAll(onePair);
        finalOrderedList.addAll(twoPair);
        finalOrderedList.addAll(threeOfAKind);
        finalOrderedList.addAll(fullHouse);
        finalOrderedList.addAll(fourOfAKind);
        finalOrderedList.addAll(fiveOfAKind);

        return finalOrderedList;
    }

    public ArrayList<Long> multiply(ArrayList<Hand> arrayList) {
        ArrayList<Long> multiplyResults = new ArrayList<>();

        for (Hand hand : arrayList) {
            long index = arrayList.indexOf(hand);
            long result = (index + 1) * hand.bid;
            multiplyResults.add(result);
        }
        return multiplyResults;
    }

    public long sumMultiplyResults(ArrayList<Long> arrayList) {
        long sumResult = 0;

        for (Long element : arrayList) {
            sumResult += element;
        }
        return sumResult;
    }

}
