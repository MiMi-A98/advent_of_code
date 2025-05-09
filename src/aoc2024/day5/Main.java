package aoc2024.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader("src/aoc2024/day5/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            List<int[]> rulesList = new ArrayList<>();
            List<List<Integer>> pagesUpdateLists = new ArrayList<>();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {

                if (inputLine.matches("\\d{2}\\|\\d{2}")) {
                    String[] splitLine = inputLine.split("\\|");
                    int[] rule = Arrays.stream(splitLine).mapToInt(Integer::parseInt).toArray();
                    rulesList.add(rule);

                } else if (inputLine.contains(",")) {
                    String[] splitLine = inputLine.split(",");
                    List<Integer> pages = Arrays.stream(splitLine).mapToInt(Integer::parseInt).boxed().toList();
                    pagesUpdateLists.add(pages);
                }
            }

            int[][] rules = new int[rulesList.size()][];

            for (int i = 0; i < rulesList.size(); i++) {
                rules[i] = rulesList.get(i);
            }

            List<List<Integer>> orderedPages = new ArrayList<>();
            List<List<Integer>> unorderedPages = new ArrayList<>();
            boolean isValidList;

            for (List<Integer> pages : pagesUpdateLists) {
                isValidList = true;

                for (int[] rule : rules) {
                    int value1 = rule[0];
                    int value2 = rule[1];

                    if (!pages.contains(value1) || !pages.contains(value2)) continue;

                    if (pages.indexOf(value1) > pages.indexOf(value2)) {
                        unorderedPages.add(pages);
                        isValidList = false;
                        break;
                    }
                }

                if (isValidList) {
                    orderedPages.add(pages);
                }
            }

            List<List<Integer>> reorderedPages = orderList(unorderedPages, rules);

            int sumPart1 = calculateSum(getMiddlePages(orderedPages));
            int sumPart2 = calculateSum(getMiddlePages(reorderedPages));

            System.out.println(sumPart1 + "\n" + sumPart2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<List<Integer>> orderList(List<List<Integer>> unorderedLists, int[][] orderingRules) {
        List<List<Integer>> reorderedPages = new ArrayList<>();
        List<Integer> ordered;
        boolean swapped;

        for (List<Integer> list : unorderedLists) {
            ordered = new ArrayList<>(list);
            do {
                swapped = false;
                for (int[] rule : orderingRules) {
                    int value1 = rule[0];
                    int value2 = rule[1];

                    if (!list.contains(value1) || !list.contains(value2)) continue;

                    int index1 = ordered.indexOf(value1);
                    int index2 = ordered.indexOf(value2);
                    if (index1 > index2) {
                        ordered.set(index1, value2);
                        ordered.set(index2, value1);
                        swapped = true;
                    }
                }
            } while (swapped);
            reorderedPages.add(ordered);
        }
        return reorderedPages;
    }

    private static List<Integer> getMiddlePages(List<List<Integer>> lists) {
        List<Integer> middlePages = new ArrayList<>();

        for (List<Integer> list : lists) {
            int middleIndex = (list.size() - 1) / 2;
            int middlePage = list.get(middleIndex);
            middlePages.add(middlePage);
        }

        return middlePages;
    }

    private static int calculateSum(List<Integer> valuesList) {
        int sum = 0;
        for (int value : valuesList) {
            sum += value;
        }
        return sum;
    }
}
