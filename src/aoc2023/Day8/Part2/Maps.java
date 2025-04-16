package aoc2023.Day8.Part2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Maps {

    public record Directions(String leftDirection, String rightDirection) {
    }

    Map<String, Directions> nodesMap;
    List<String> startingNodeList = new ArrayList<>();

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


    public void selectStartingNodesFromMap() {
        startingNodeList = nodesMap.keySet().stream().filter(k -> k.endsWith("A")).toList();
    }


    public List<Integer> getStepsListToLastNodes(List<Character> characterList) {
        List<Integer> stepsCountList = new ArrayList<>();
        selectStartingNodesFromMap();

        for (int i = 0; i < startingNodeList.size(); i++) {
            int stepsCount = 0;
            String currentNode = startingNodeList.get(i);
            while (!currentNode.contains("Z")) {
                for (Character character : characterList) {
                    while (!currentNode.contains("Z")) {

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
            stepsCountList.add(stepsCount);
        }

        return stepsCountList;
    }

}
