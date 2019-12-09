package com.census;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

public class StateCensusAnalyser <T> {
    private String POPULATION_FILE = "/home/admin97/Ayush/github/MyCensusAnalyser/src/main/resources/populationwise_sort.json";
    private String POPULATION_DENSITY_FILE = "/home/admin97/Ayush/github/MyCensusAnalyser/src/main/resources/populationDensitywise_sort.json";
    private String AREA_FILE = "/home/admin97/Ayush/github/MyCensusAnalyser/src/main/resources/areawise_sort.json";
    private String NAME_FILE = "/home/admin97/Ayush/github/MyCensusAnalyser/src/main/resources/namewise_sort.json";

    static List<StateCensus> censusList;
    int censusCounter;

    public StateCensusAnalyser() {
    }

    public int openCsvBuilder(String csvFilePath) throws CensusAnalyserException {
        try {
            censusList = getBean(csvFilePath).parse();
            System.out.println(censusList.toString());
            //sort("population",true, POPULATION_FILE);
            return censusList.size();
        } catch (CensusAnalyserException e){
            throw e;
        } catch (RuntimeException e){
            if (censusList==null){
                throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.INCORRECT_DATA_ISSUE,
                        "delimiter error at line 1 OR might be some error " +
                                "related to headers or incorrect extension / input file ");
            }
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.INCORRECT_DATA_ISSUE,
                    "might be some error related to delimiter");
        }
    }

    private void writeThisListToJsonFile(String writeToFile) throws CensusAnalyserException {
        Gson gson = new Gson();
        String json = gson.toJson(censusList);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(writeToFile);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.SOME_OTHER_IO_EXCEPTION,
                    "Some other IO related exception");
        }
    }

    public CsvToBean<StateCensus> getBean(String csvFilePath) throws CensusAnalyserException {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(StateCensus.class)
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

    public void sort(String fieldName, boolean reverse, String filePath) throws CensusAnalyserException {
        try {
            Comparator myComparator = sortBasedOn(fieldName);
            if (myComparator != null) {
                if (reverse) {
                    Collections.sort(censusList, myComparator);
                } else {
                    Collections.sort(censusList, Collections.reverseOrder(myComparator));
                }
            }
        } catch (CensusAnalyserException e) {
            throw e;
        }
        writeThisListToJsonFile(filePath);
    }

    public Comparator<StateCensus> sortBasedOn(String fieldName) throws CensusAnalyserException {
        Comparator<StateCensus> comparator = new Comparator<StateCensus>() {
            @Override
            public int compare(StateCensus obj1, StateCensus obj2) {
                try {
                    Field thisField = StateCensus.class.getDeclaredField(fieldName);
                    thisField.setAccessible(true);
                    Comparable stateCensusField1 = (Comparable) thisField.get(obj1);
                    Comparable stateCensusField2 = (Comparable) thisField.get(obj2);
                    return stateCensusField1.compareTo(stateCensusField2);
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                    try {
                        throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.INCORRECT_COMPARE_FIELD,
                                "Incorrect field selected for comparator...please enter correct field");
                    } catch (CensusAnalyserException ex) {
                        ex.printStackTrace();
                    }
                }
                return Integer.parseInt(null);
            }
        };
        return comparator;
    }
}
