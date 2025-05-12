package aoc2024.day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    private record Coordinates(int row, int column) {
    }

    private record Point(Main.Coordinates coordinates, Main.Directions directions) {
    }

    enum Directions {
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1);

        final int directionRow;
        final int directionColumn;

        Directions(int directionRow, int directionColumn) {
            this.directionRow = directionRow;
            this.directionColumn = directionColumn;
        }

        Directions turnRight() {
            return values()[(this.ordinal() + 1) % values().length];
        }
    }

    static char[][] map;

    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader("src/aoc2024/day6/Input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            String inputLine;
            List<char[]> inputRows = new ArrayList<>();

            while ((inputLine = reader.readLine()) != null) {
                char[] lineArray = inputLine.toCharArray();
                inputRows.add(lineArray);
            }

            map = new char[inputRows.size()][];

            for (int i = 0; i < inputRows.size(); i++) {
                map[i] = inputRows.get(i);
            }

            int guardMovementsCount = getGuardMovementsCount();
            int possibleObstructionsCount = countPossibleObstructionPoints();

            System.out.println(guardMovementsCount + "\n" + possibleObstructionsCount);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static Point getStartPoint() {
        Point startPoint = null;

        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {

                switch (map[row][column]) {
                    case '^' -> startPoint = new Point(new Coordinates(row, column), Directions.UP);
                    case '>' -> startPoint = new Point(new Coordinates(row, column), Directions.RIGHT);
                    case '<' -> startPoint = new Point(new Coordinates(row, column), Directions.LEFT);
                    case 'v' -> startPoint = new Point(new Coordinates(row, column), Directions.DOWN);
                }
            }
        }

        return startPoint;
    }

    private static int getGuardMovementsCount() {
        Point startPoint = getStartPoint();
        Coordinates currentCoord = startPoint.coordinates();
        Directions direction = startPoint.directions();

        Set<Coordinates> visited = new HashSet<>();
        visited.add(currentCoord);

        while (true) {
            int nextRow = currentCoord.row() + direction.directionRow;
            int nextColumn = currentCoord.column() + direction.directionColumn;

            boolean outOfBound = nextRow < 0 || nextRow >= map.length
                    || nextColumn < 0 || nextColumn >= map[0].length;

            boolean blocked = !outOfBound && map[nextRow][nextColumn] == '#';

            if (outOfBound) break;
            if (blocked) direction = direction.turnRight();
            else {
                currentCoord = new Coordinates(nextRow, nextColumn);
                visited.add(currentCoord);
            }
        }

        return visited.size();
    }

    private static char[][] cloneMap() {
        char[][] copy = new char[map.length][];

        for (int i = 0; i < map.length; i++) {
            copy[i] = map[i].clone();
        }

        return copy;
    }

    private static boolean wouldLoop(char[][] map, Point startPoint) {
        Coordinates currentCoordinates = startPoint.coordinates();
        Directions direction = startPoint.directions();

        Set<Point> seenMovements = new HashSet<>();

        while (true) {
            Point point = new Point(currentCoordinates, direction);

            if (!seenMovements.add(point)) {
                return true;
            }

            int nextRow = currentCoordinates.row() + direction.directionRow;
            int nextColumn = currentCoordinates.column() + direction.directionColumn;

            boolean outOfBound = nextRow < 0 || nextRow >= map.length
                    || nextColumn < 0 || nextColumn >= map[0].length;

            boolean blocked = !outOfBound && map[nextRow][nextColumn] == '#';

            if (outOfBound) return false;
            if (blocked) direction = direction.turnRight();
            else {
                currentCoordinates = new Coordinates(nextRow, nextColumn);
            }
        }
    }

    private static int countPossibleObstructionPoints() {
        Point startPoint = getStartPoint();
        Coordinates startCoordinates = startPoint.coordinates();

        List<Coordinates> possibleObstructionPoints = new ArrayList<>();

        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {

                if (map[row][column] != '.' || (row == startCoordinates.row && column == startCoordinates.column))
                    continue;

                char[][] testMap = cloneMap();
                testMap[row][column] = '#';

                if (wouldLoop(testMap, startPoint)) {
                    possibleObstructionPoints.add(new Coordinates(row, column));
                }
            }
        }

        return possibleObstructionPoints.size();
    }
}
