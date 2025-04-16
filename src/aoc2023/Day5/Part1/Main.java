package aoc2023.Day5.Part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    static ArrayList<Long> seeds = new ArrayList<>();
    static ConvertingMap seedToSoil = new ConvertingMap();
    static ConvertingMap soilToFertilizer = new ConvertingMap();
    static ConvertingMap fertilizerToWater = new ConvertingMap();
    static ConvertingMap waterToLight = new ConvertingMap();
    static ConvertingMap lightToTemperature = new ConvertingMap();
    static ConvertingMap temperatureToHumidity = new ConvertingMap();
    static ConvertingMap humidityToLocation = new ConvertingMap();
    static ArrayList<Long> seedsLocations = new ArrayList<>();

    public static void main(String[] args) {

        String inputFileName = "src/aoc2023.Day5/Input2.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {

                if (inputLine.contains("seeds: ")) {
                    String[] lineSplit = inputLine.split(":");
                    String[] seedNumbers = lineSplit[1].trim().split("\\s+");

                    for (String seed : seedNumbers) {
                        long foundSeedNumber = Long.parseLong(seed);
                        seeds.add(foundSeedNumber);
                    }

                } else if (inputLine.contains("seed-to-soil map:")) {

                    while (!((inputLine = bufferedReader.readLine()).isBlank())) {
                        String[] lineNumbers = inputLine.split("\\s+");
                        long destinationRange = Long.parseLong(lineNumbers[0]);
                        long sourceRange = Long.parseLong(lineNumbers[1]);
                        long rangeLength = Long.parseLong(lineNumbers[2]);

                        seedToSoil.addRange(destinationRange, sourceRange, rangeLength);
                    }

                } else if (inputLine.contains("soil-to-fertilizer map:")) {

                    while (!((inputLine = bufferedReader.readLine()).isBlank())) {
                        String[] lineNumbers = inputLine.split("\\s+");
                        long destinationRange = Long.parseLong(lineNumbers[0]);
                        long sourceRange = Long.parseLong(lineNumbers[1]);
                        long rangeLength = Long.parseLong(lineNumbers[2]);

                        soilToFertilizer.addRange(destinationRange, sourceRange, rangeLength);
                    }

                } else if (inputLine.contains("fertilizer-to-water map:")) {

                    while (!((inputLine = bufferedReader.readLine()).isBlank())) {
                        String[] lineNumbers = inputLine.split("\\s+");
                        long destinationRange = Long.parseLong(lineNumbers[0]);
                        long sourceRange = Long.parseLong(lineNumbers[1]);
                        long rangeLength = Long.parseLong(lineNumbers[2]);

                        fertilizerToWater.addRange(destinationRange, sourceRange, rangeLength);
                    }

                } else if (inputLine.contains("water-to-light map:")) {

                    while (!((inputLine = bufferedReader.readLine()).isBlank())) {
                        String[] lineNumbers = inputLine.split("\\s+");
                        long destinationRange = Long.parseLong(lineNumbers[0]);
                        long sourceRange = Long.parseLong(lineNumbers[1]);
                        long rangeLength = Long.parseLong(lineNumbers[2]);

                        waterToLight.addRange(destinationRange, sourceRange, rangeLength);
                    }

                } else if (inputLine.contains("light-to-temperature map:")) {

                    while (!((inputLine = bufferedReader.readLine()).isBlank())) {
                        String[] lineNumbers = inputLine.split("\\s+");
                        long destinationRange = Long.parseLong(lineNumbers[0]);
                        long sourceRange = Long.parseLong(lineNumbers[1]);
                        long rangeLength = Long.parseLong(lineNumbers[2]);

                        lightToTemperature.addRange(destinationRange, sourceRange, rangeLength);
                    }

                } else if (inputLine.contains("temperature-to-humidity map:")) {

                    while (!((inputLine = bufferedReader.readLine()).isBlank())) {
                        String[] lineNumbers = inputLine.split("\\s+");
                        long destinationRange = Long.parseLong(lineNumbers[0]);
                        long sourceRange = Long.parseLong(lineNumbers[1]);
                        long rangeLength = Long.parseLong(lineNumbers[2]);

                        temperatureToHumidity.addRange(destinationRange, sourceRange, rangeLength);
                    }

                } else if (inputLine.contains("humidity-to-location map:")) {

                    while ((inputLine = bufferedReader.readLine()) != null) {
                        String[] lineNumbers = inputLine.split("\\s+");
                        long destinationRange = Long.parseLong(lineNumbers[0]);
                        long sourceRange = Long.parseLong(lineNumbers[1]);
                        long rangeLength = Long.parseLong(lineNumbers[2]);

                        humidityToLocation.addRange(destinationRange, sourceRange, rangeLength);

                    }

                }
            }

           for (long seed : seeds){
               long soil = seedToSoil.getDestinationPosition(seed);
               long fertilizer =  soilToFertilizer.getDestinationPosition(soil);
               long water = fertilizerToWater.getDestinationPosition(fertilizer);
               long light = waterToLight.getDestinationPosition(water);
               long temperature = lightToTemperature.getDestinationPosition(light);
               long humidity = temperatureToHumidity.getDestinationPosition(temperature);
               long location = humidityToLocation.getDestinationPosition(humidity);
               seedsLocations.add(location);
           }


            System.out.println(seedsLocations);
            System.out.println(Collections.min(seedsLocations));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
