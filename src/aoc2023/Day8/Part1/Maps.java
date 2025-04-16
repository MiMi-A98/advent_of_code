package aoc2023.Day8.Part1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Maps {

    public record Directions(String leftDirection, String rightDirection) {
    }

    Map<String, Directions> nodesMap;

    public Maps() {
        nodesMap = new HashMap<>();
    }

    public void addMap(String node, String leftDirection, String rightDirection) {
        Directions nodeDirections = new Directions(leftDirection, rightDirection);
        nodesMap.put(node, nodeDirections);
    }

    @Override
    public String toString() {
        return "{" + nodesMap + '}';
    }


    public int getStepsNumbToLastNode(List<Character> arrayList) {
        int stepsCount = 0;
        String currentNode = "AAA";

        while (!currentNode.equals("ZZZ")) {
            for (Character character : arrayList) {
                while (!currentNode.equals("ZZZ")) {

                    int nextStep = Main.getDirectionValue(character);
                    Directions directions = nodesMap.get(currentNode);
                    if (nextStep == 0) {
                        currentNode = directions.leftDirection;
                        stepsCount += 1;
                        break;
                    } else if (nextStep == 1) {
                        currentNode = directions.rightDirection;
                        stepsCount += 1;
                        break;
                    }

                }
            }
        }

        return stepsCount;
    }

}
