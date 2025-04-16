package aoc2023.Day6.Part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String inputFileName = "src/aoc2023.Day6/Input2.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String inputLine;
            long raceTime = 0;
            long  raceDistance = 0;
            while ((inputLine = bufferedReader.readLine()) != null) {

                if (inputLine.contains("Time: ")) {
                    String[] lineSplit =  inputLine.replaceAll("\\s+", "").split(":");
                    String raceTimeString = lineSplit[1];
                    raceTime = Long.parseLong(raceTimeString);

                } else if (inputLine.contains("Distance: ")) {
                    String[] lineSplit = inputLine.replaceAll("\\s+", "").split(":");
                    String raceDistanceString = lineSplit[1];
                    raceDistance = Long.parseLong(raceDistanceString);
                }

            }

                long raceTimeLeft;
                long distance;
                long pressTime;
                long numbChancesToBeatRecord = 0;

                for (pressTime = 0; pressTime <= raceTime; pressTime++) {
                    raceTimeLeft = raceTime - pressTime;
                    distance = pressTime * raceTimeLeft;

                    if (distance > raceDistance) {
                        numbChancesToBeatRecord += 1;
                    }
                }

            System.out.println(numbChancesToBeatRecord);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}







