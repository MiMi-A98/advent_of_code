package aoc2023.Day6.Part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    static ArrayList<Integer> completeRaceTimes = new ArrayList<>();
    static ArrayList<Integer> recordDistances = new ArrayList<>();

    public static void main(String[] args) {
        String inputFileName = "src/aoc2023.Day6/Input2.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {

                if (inputLine.contains("Time: ")) {
                    String[] lineSplit = inputLine.split(":");
                    String[] raceTimes = lineSplit[1].trim().split("\\s+");

                    for (String time : raceTimes) {
                        int raceTime = Integer.parseInt(time);
                        completeRaceTimes.add(raceTime);
                    }

                } else if (inputLine.contains("Distance: ")) {
                    String[] lineSplit = inputLine.split(":");
                    String[] raceDistances = lineSplit[1].trim().split("\\s+");

                    for (String distance : raceDistances) {
                        int raceDistance = Integer.parseInt(distance);
                        recordDistances.add(raceDistance);
                    }

                }

            }

            ArrayList<Integer> chancesToBeatRecord = new ArrayList<>();

            for (int i = 0; i < completeRaceTimes.size(); i++) {
                int raceTimeLeft;
                int distance;
                int pressTime;
                int numbChancesToBeatRecord = 0;

                for (pressTime = 0; pressTime <= completeRaceTimes.get(i); pressTime++) {
                    raceTimeLeft = completeRaceTimes.get(i) - pressTime;
                    distance = pressTime * raceTimeLeft;

                    if (distance > recordDistances.get(i)) {
                        numbChancesToBeatRecord += 1;
                    }
                }
                chancesToBeatRecord.add(numbChancesToBeatRecord);
            }

            System.out.println(multiply(chancesToBeatRecord));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int multiply(ArrayList<Integer> arrayList) {
        int result = 1;

        for (int i = 0; i < arrayList.size(); i++) {
            result *= arrayList.get(i);
        }

        return result;
    }
}







