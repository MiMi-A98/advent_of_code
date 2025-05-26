package aoc2024.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader("src/aoc2024/day11/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;
            List<String> stonesLine = new ArrayList<>();

            while ((inputLine = reader.readLine()) != null) {
                stonesLine = Arrays.stream(inputLine.split(" ")).collect(Collectors.toCollection(ArrayList::new));
            }

            Map<String, Long> part1 = generateRocks(25, stonesLine);
            List<Long> newRocks = part1.values().stream().toList();
            long sumPart1 = calculateSum(newRocks);

            Map<String, Long> part2 = generateRocks(75, stonesLine);
            List<Long> newRocks2 = part2.values().stream().toList();
            long sumPart2 = calculateSum(newRocks2);

            System.out.println(sumPart1 + "\n" + sumPart2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

/*  Solution for first part
    Commented because solution for part 2 works for both parts,
    but I didn't want to erase this solution

    private static void part1(int blinks, List<String> stonesLine) {
        for (int i = 0; i < blinks; i++) {
            List<String> temporarilyList = new ArrayList<>();
            for (int j = 0; j < stonesLine.size(); j++) {
                String stone = stonesLine.get(j);
                if (stone.equals("0")) {
                    String s = "1";
                    temporarilyList.add(s);

                } else if (stone.length() % 2 == 0) {
                    int mid = stone.length() / 2;
                    String left = stone.substring(0, mid);
                    String right = stone.substring(mid);

                    left = left.replaceFirst("^0+(?!$)", "");
                    right = right.replaceFirst("^0+(?!$)", "");

                    temporarilyList.add(left);
                    temporarilyList.add(right);
                } else {
                    long newStoneValue = Long.parseLong(stone) * 2024;
                    temporarilyList.add(String.valueOf(newStoneValue));
                }
            }

            stonesLine.clear();
            stonesLine.addAll(temporarilyList);
        }
    }

*/

    private static Map<String, Long> generateRocks(int blinks, List<String> stonesLine) {
        Map<String, Long> newStones = new HashMap<>();

        for (String stone : stonesLine) {
            long count = newStones.getOrDefault(stone, 0L);
            newStones.put(stone, ++count);
        }

        for (int i = 0; i < blinks; i++) {
            Map<String, Long> temporaryRocks = new HashMap<>();

            for (Map.Entry<String, Long> entry : newStones.entrySet()) {
                String current = entry.getKey();
                Long occurrences = entry.getValue();

                if (current.equals("0")) {
                    String s = "1";
                    temporaryRocks.merge(s, occurrences, Long::sum);
                } else if (current.length() % 2 == 0) {
                    int mid = current.length() / 2;
                    String left = current.substring(0, mid);
                    String right = current.substring(mid);

                    left = left.replaceFirst("^0+(?!$)", "");
                    right = right.replaceFirst("^0+(?!$)", "");

                    temporaryRocks.merge(left, occurrences, Long::sum);
                    temporaryRocks.merge(right, occurrences, Long::sum);
                } else {
                    long newStoneValue = Long.parseLong(current) * 2024;
                    temporaryRocks.merge(String.valueOf(newStoneValue), occurrences, Long::sum);
                }
            }
            newStones = temporaryRocks;
        }
        return newStones;
    }

    private static long calculateSum(List<Long> values) {
        long sum = 0;

        for (long value : values) {
            sum += value;
        }
        return sum;
    }
}
