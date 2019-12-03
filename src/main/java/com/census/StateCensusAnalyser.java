package com.census;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

public class StateCensusAnalyser {
    private static String POPULATION_FILE = "/home/admin97/Ayush/github/MyCensusAnalyser/src/main/resources/populationwise_sort.json";
    private static String NAME_FILE = "/home/admin97/Ayush/github/MyCensusAnalyser/src/main/resources/namewise_sort.json";
    static List<StateCensus> censusList;
    static int censusCounter;

    public StateCensusAnalyser() {
    }

    public static <T>  int openCsvBuilder(String csvFilePath, Object myClass) throws CensusAnalyserException {
        try {
            censusList = (getBean(csvFilePath, myClass)).parse();
            System.out.println(censusList.toString());
            sortThisListBasedOnStateName(censusList);
            sortThisListBasedOnPopulation(censusList);
            return censusList.size();
        } catch (CensusAnalyserException e){
            throw e;
        } catch (RuntimeException e){
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.DELIMITER_ISSUE,
                    "might be some error related to delimiter at record no. : " +(censusCounter+1));
        }
    }

    private static void writeThisListToJsonFile(String writeToFile) {
        Gson gson = new Gson();
        String json = gson.toJson(censusList);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(writeToFile);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CsvToBean<StateCensus> getBean(String csvFilePath, Object myClass) throws CensusAnalyserException {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType((Class) myClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean;
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.NO_SUCH_FILE,
                    "no such file exists. Please enter correct file");
        } catch (RuntimeException e){
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.INCORRECT_DATA_ISSUE,
                    "delimiter error at line 1 OR might be some error " +
                            "related to headers or incorrect extension / input file ");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.SOME_OTHER_IO_EXCEPTION,
                    "Some other IO related exception");
        }
    }

    private static void sortThisListBasedOnStateName(List<StateCensus> censusList) {
        Comparator<StateCensus> c = Comparator.comparing(StateCensus::getStateName);
        censusList.sort(c);
        writeThisListToJsonFile(NAME_FILE);
    }

    private static void sortThisListBasedOnPopulation(List<StateCensus> censusList) {
        Comparator<StateCensus> c = (s1, s2) -> Integer.parseInt(s2.getPopulation()) - Integer.parseInt(s1.getPopulation());
        censusList.sort(c);
        writeThisListToJsonFile(POPULATION_FILE);
    }


}
