package aoc2023.Day3.Part2;

public class Coordinates {
    private int rowIndex;
    private int startNumberColumnIndex;
    private int endNumberColumnIndex;

    private int columnIndex;
    public Coordinates() {
    }

    public Coordinates(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public Coordinates(int rowIndex, int startNumberColumnIndex, int endNumberColumnIndex) {
        this.rowIndex = rowIndex;
        this.startNumberColumnIndex = startNumberColumnIndex;
        this.endNumberColumnIndex = endNumberColumnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getStartNumberColumnIndex() {
        return startNumberColumnIndex;
    }

    public int getEndNumberColumnIndex() {
        return endNumberColumnIndex;
    }


    @Override
    public String toString() {
        return "{" + getRowIndex() + " , " + getStartNumberColumnIndex() + " , " + getEndNumberColumnIndex() + "}";
    }


}
