package aoc2024.day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader("src/aoc2024/day9/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            List<String> input = new ArrayList<>();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                String[] inputArray = inputLine.split("");
                input = Arrays.stream(inputArray).toList();
            }

            List<String> fileSystemPart1 = moveFileBlocks(generateFileSystem(input));
            List<String> fileSystemPart2 = moveFiles(generateFileSystem(input));

            long sumPart1 = calculateCheckSum(fileSystemPart1);
            long sumPart2 = calculateCheckSum(fileSystemPart2);

            System.out.println(sumPart1 + "\n" + sumPart2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static List<String> generateFileSystem(List<String> input) {
        List<String> fileSystem = new ArrayList<>();
        int id = 0;
        for (int i = 0; i < input.size(); i++) {

            String character = input.get(i);
            int value = Integer.parseInt(character);

            for (int j = 0; j < value; j++) {
                if (i % 2 == 0) {
                    fileSystem.add(String.valueOf(id));
                } else {
                    fileSystem.add(".");
                }
            }

            if (i % 2 == 0) {
                id++;
            }
        }
        return fileSystem;
    }

    private static List<String> moveFileBlocks(List<String> fileSystem) {

        for (int i = 0; i < fileSystem.size(); i++) {
            char leftChar = fileSystem.get(i).charAt(0);

            if (!Character.isDigit(leftChar)) {
                for (int j = fileSystem.size() - 1; j > 0; j--) {
                    char rightChar = fileSystem.get(j).charAt(0);

                    if (j <= i) break;

                    if (Character.isDigit(rightChar)) {
                        String replacement = fileSystem.get(j);
                        fileSystem.set(i, replacement);
                        fileSystem.set(j, ".");
                        break;
                    }
                }
            }
        }
        return fileSystem;
    }

    private static List<String> moveFiles(List<String> fileSystem) {
        int fileSystemSize = fileSystem.size();
        Map<String, int[]> filesMap = new HashMap<>();

        for (int i = 0; i < fileSystemSize; ) {
            String fileBlock = fileSystem.get(i);

            if (fileBlock.equals(".")) {
                i++;
                continue;
            }

            int start = i;
            while (i < fileSystemSize && fileSystem.get(i).equals(fileBlock)) {
                i++;
            }
            filesMap.put(fileBlock, new int[]{start, i - start});
        }

        List<String> fileIds = new ArrayList<>(filesMap.keySet());
        fileIds.sort((a, b) -> Integer.parseInt(b) - Integer.parseInt(a));



        for (String id : fileIds) {
            int[] fileSize = filesMap.get(id);
            int oldStart = fileSize[0];
            int oldEnd = fileSize[1];
            int target = -1;

            outer:
            for (int i = 0; i <= oldStart - oldEnd; i++) {
                if (fileSystem.get(i).equals(".")) {
                    for (int j = 0; j < oldEnd; j++) {
                        if (!fileSystem.get(i + j).equals(".")) {
                            i = i + j;
                            continue outer;
                        }

                    }
                    target = i;
                    break;
                }
            }

            if (target >= 0) {
                for (int i = 0; i < oldEnd; i++) {
                    fileSystem.set(target + i, id);
                }
                for (int i = 0; i < oldEnd; i++) {
                    fileSystem.set(oldStart + i, ".");
                }
                filesMap.put(id, new int[]{target, oldEnd});
            }
        }

        return fileSystem;
    }

    private static long calculateCheckSum(List<String> fileSystem) {
        long sum = 0;
        int index = 0;
        for (String string : fileSystem) {
            if (string.matches("\\d+")) {
                int value = Integer.parseInt(string);
                sum += (long) index * value;
            }
            index++;
        }
        return sum;
    }
}
