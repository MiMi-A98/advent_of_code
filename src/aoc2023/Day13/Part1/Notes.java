package aoc2023.Day13.Part1;

import java.util.ArrayList;
import java.util.List;

public class Notes {
    private final List<String> pattern;
    private static final List<Integer> verticalMirrors = new ArrayList<>();
    private static final List<Integer> horizontalMirrors = new ArrayList<>();

    public Notes(List<String> list) {
        pattern = list;
    }

    private String getColumn(int columnIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pattern.size(); i++) {
            String line = pattern.get(i);
            stringBuilder.append(line.charAt(columnIndex));
        }
        return stringBuilder.toString();
    }

    public int getMirrorPosition(boolean checkColumn) {
        int maxIndex = checkColumn ? pattern.get(0).length() : pattern.size();

        for (int i = 1; i < maxIndex; i++) {
            String firstElem = checkColumn ? getColumn(i - 1) : pattern.get(i - 1);
            String secondElem = checkColumn ? getColumn(i) : pattern.get(i);

            if (firstElem.equals(secondElem)) {
                int elemToCheck = Math.min(i - 1, maxIndex - i - 1);
                boolean existMirror = true;

                for (int index = 0; index < elemToCheck; index++) {
                    String first = checkColumn ? getColumn(i - index - 2) : pattern.get(i - index - 2);
                    String second = checkColumn ? getColumn(i + index + 1) : pattern.get(i + index + 1);

                    if (!first.equals(second)) {
                        existMirror = false;
                        break;
                    }
                }
                if (existMirror) {
                    return i;
                }
            }
        }
        return 0;
    }

    public static int calculateHorizontalMirrorsSum() {
        int horizontalSum = 0;
        for (int mirror : horizontalMirrors) {
            int multiply = mirror * 100;
            horizontalSum += multiply;
        }
        return horizontalSum;
    }

    public static int calculateVerticalMirrorsSum() {
        int vericalSum = 0;
        for (int mirror : verticalMirrors) {
            vericalSum += mirror;
        }
        return vericalSum;
    }

    public void findHorizontalMirrors() {
        int horizontalMirror = getMirrorPosition(false);
        horizontalMirrors.add(horizontalMirror);
    }

    public void findVerticalMirrors() {
        int verticalMirror = getMirrorPosition(true);
        verticalMirrors.add(verticalMirror);
    }

    public static int calculateMirrorsSum() {
        return calculateHorizontalMirrorsSum() + calculateVerticalMirrorsSum();
    }

    @Override
    public String toString() {
        return "Pattern{" +
                "pattern=" + pattern +
                '}';
    }
}
