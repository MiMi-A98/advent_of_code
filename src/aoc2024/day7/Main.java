package aoc2024.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader("src/aoc2024/day7/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            List<Long> testValues = new ArrayList<>();
            List<long[]> equationValues = new ArrayList<>();

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                String[] splitLine = inputLine.split(": ");

                long[] calibrationValues = Arrays.stream(splitLine[1].split(" ")).mapToLong(Integer::parseInt).toArray();

                testValues.add(Long.valueOf(splitLine[0]));
                equationValues.add(calibrationValues);
            }

            List<Long> validTestsPart1 = new ArrayList<>();
            List<Long> validTestsPart2 = new ArrayList<>();

            for (int i = 0; i < testValues.size(); i++) {

                long testValue = testValues.get(i);
                long[] calibrationValues = equationValues.get(i);

                List<Integer> binaryMasks = generateBinaryMasks(calibrationValues.length);
                List<Integer> ternaryMasks = generateTernaryMasks(calibrationValues.length);

                for (int mask : binaryMasks) {
                    long result = calculateEquation(calibrationValues, mask);
                    if (result == testValue) {
                        validTestsPart1.add(testValue);
                        break;
                    }
                }

                for (int mask : ternaryMasks) {
                    long result = calculateEquationWithConcat(calibrationValues, mask);
                    if (result == testValue) {
                        validTestsPart2.add(testValue);
                        break;
                    }
                }
            }

            long testValuesSum1 = sum(validTestsPart1);
            long testValuesSum2 = sum(validTestsPart2);

            System.out.println(testValuesSum1 + "\n" + testValuesSum2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<Integer> generateBinaryMasks(int size) {
        List<Integer> masks = new ArrayList<>();
        int total = 1 << (size - 1);

        for (int i = 0; i < total; i++) {
            masks.add(i);
        }

        return masks;
    }

    private static List<Integer> generateTernaryMasks(int size) {
        List<Integer> masks = new ArrayList<>();
        int slots = size - 1;
        int total = (int) Math.pow(3, slots);

        for (int i = 0; i < total; i++) {
            masks.add(i);
        }

        return masks;
    }

    private static long calculateEquation(long[] numbers, int mask) {
        long result = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            boolean isMultiplication = ((mask >> (i - 1)) & 1) == 1;
            result = isMultiplication ? (result * numbers[i]) : (result + numbers[i]);
        }

        return result;
    }

    private static long calculateEquationWithConcat(long[] numbers, int mask) {
        long result = numbers[0];
        int slots = numbers.length - 1;

        int[] power3 = new int[slots];
        power3[0] = 1;

        for (int i = 1; i < slots; i++) {
            power3[i] = power3[i - 1] * 3;
        }

        for (int i = 1; i < numbers.length; i++) {
            int operation = (mask / power3[i - 1]) % 3;
            long value = numbers[i];

            if (operation == 0) {
                result = result + value;
            } else if (operation == 1) {
                result = result * value;
            } else {
                int digits = (int) Math.log10(value) + 1;
                result = result * (long) Math.pow(10, digits) + value;
            }
        }
        return result;
    }

    private static long sum(List<Long> values) {
        long result = 0;

        for (long value : values) {
            result += value;
        }

        return result;
    }
}

