package aoc2023.Day12.Part2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringConditionRecord {
    String spring;
    List<Integer> groups;

    public SpringConditionRecord(String spring, List<Integer> groups) {
        this.spring = spring;
        this.groups = groups;
    }

    public void unfoldRecord(int numOfCopies) {
        this.spring = unfoldSprings(numOfCopies);
        this.groups = unfoldGroups(numOfCopies);
    }

    private String unfoldSprings(int numOfCopies) {
        String unfoldedSpring = spring;

        for (int i = 1; i < numOfCopies; i++) {
            unfoldedSpring = unfoldedSpring.concat("?").concat(spring);
        }

        return unfoldedSpring;
    }

    private List<Integer> unfoldGroups(int numOfCopies) {
        List<Integer> unfoldedGroups = new ArrayList<>();

        for (int i = 0; i < numOfCopies; i++) {
            unfoldedGroups.addAll(groups);
        }

        return unfoldedGroups;
    }

    private static final Map<SpringConditionRecord, Long> arrangementCache = new HashMap<>();

    public long countArrangements() {

        long arrangementCount = 0;

        if (arrangementCache.containsKey(this)) {
            return arrangementCache.get(this);
        }

        if (spring.isBlank()) {
            return groups.isEmpty() ? 1 : 0;
        }

        char firstChar = spring.charAt(0);

        if (firstChar == '.') {
            arrangementCount = new SpringConditionRecord(spring.substring(1), groups).countArrangements();
        } else if (firstChar == '?') {
            arrangementCount = new SpringConditionRecord("." + spring.substring(1), groups).countArrangements()
                    + new SpringConditionRecord("#" + spring.substring(1), groups).countArrangements();
        } else {
            // firstChar == #
            if (groups.isEmpty()) {
                arrangementCount = 0;
            } else {

                int damagedCount = groups.get(0);
                if (damagedCount <= spring.length() && spring.chars().limit(damagedCount).allMatch(c -> c == '#' || c == '?')) {
                    List<Integer> newGroups = groups.subList(1, groups.size());

                    if (damagedCount == spring.length()) {
                        arrangementCount = newGroups.isEmpty() ? 1 : 0;
                    } else if (spring.charAt(damagedCount) == '.') {
                        arrangementCount = new SpringConditionRecord(spring.substring(damagedCount + 1), newGroups).countArrangements();
                    } else if (spring.charAt(damagedCount) == '?') {
                        arrangementCount = new SpringConditionRecord("." + spring.substring(damagedCount + 1), newGroups).countArrangements();
                    } else {
                        arrangementCount = 0;
                    }

                } else {
                    arrangementCount = 0;
                }
            }
        }
        arrangementCache.put(this, arrangementCount);

        return arrangementCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpringConditionRecord that = (SpringConditionRecord) o;

        if (!spring.equals(that.spring)) return false;
        return groups.equals(that.groups);
    }

    @Override
    public int hashCode() {
        int result = spring.hashCode();
        result = 31 * result + groups.hashCode();
        return result;
    }
}

