package aoc2023.Day5.Part2;

import java.util.ArrayList;

public class ConvertingMap {
    private record Range(long destinationRangeStart, long sourceRangeStart, long rangeLength) {

    }
    private ArrayList<Range> ranges;

    public ConvertingMap() {
        ranges = new ArrayList<>();
    }

    public void addRange(long destinationRangeStart, long sourceRangeStart, long rangeLength) {
        Range range = new Range(destinationRangeStart, sourceRangeStart, rangeLength);
        ranges.add(range);
    }

    @Override
    public String toString() {
        return "ConvertingMap{" +
                "ranges=" + ranges +
                '}';
    }


    public long getDestinationPosition(long sourceNumber) {
        Long destinationPosition = null;

        for (Range range : ranges) {
            if ((sourceNumber >= range.sourceRangeStart) && (sourceNumber < (range.sourceRangeStart + range.rangeLength))) {
                destinationPosition = sourceNumber + (range.destinationRangeStart - range.sourceRangeStart);
                return destinationPosition;
            }
        }

        destinationPosition = sourceNumber;

        return destinationPosition;
    }
}


