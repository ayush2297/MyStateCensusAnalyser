package com.census;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class StateCensusAnalyserTest {
    public static final String STATECODES_CSVFILE = "/home/admin97/Ayush/github/MyCensusAnalyser/src/main/resources/StateCode.csv";
    public static final String STATECENSUS_CSVFILE = "/home/admin97/Ayush/github/MyCensusAnalyser/src/main/resources/StateCensusData.csv";
    public static final String WRONG_FILE = "/useless.txt";

    @Test
    public void GivenTheStateCodesCsvFile_IfHasCorrectNumberOfRecords_ShouldReturnTrue() {
        try {
            StateCensusAnalyser<StateCensus> censusAnalyser = new StateCensusAnalyser<>();
            int count = censusAnalyser.openCsvBuilder(STATECENSUS_CSVFILE);
            Assert.assertEquals(29, count);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void GivenTheStateCensusCsvFile_IfDoesntExist_ShouldThrowCensusAnalyserException() {
        try {
            StateCensusAnalyser<StateCensus> censusAnalyser = new StateCensusAnalyser<>();
            int count = censusAnalyser.openCsvBuilder(WRONG_FILE);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.NO_SUCH_FILE, e.type);
        }
    }

    @Test
    public void GivenTheStateCensusCsvFile_WhenCorrect_ButFileExtensionIncorrect_ShouldThrowCensusAnalyserException() {
        try {
            StateCensusAnalyser<StateCensus> censusAnalyser = new StateCensusAnalyser<>();
            int count = censusAnalyser.openCsvBuilder(STATECODES_CSVFILE);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.INCORRECT_DATA_ISSUE, e.type);
        }
    }

    @Test
    public void GivenTheStateCensusCSVFile_WhenCorrect_ButDelimiterIncorrect_ReturnsCensusAnalyserException() {
        try {
            StateCensusAnalyser<StateCensus> censusAnalyser = new StateCensusAnalyser<>();
            int count = censusAnalyser.openCsvBuilder(STATECENSUS_CSVFILE);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.INCORRECT_DATA_ISSUE, e.type);

        }
    }

    @Test
    public void whenCorrectCensusCSVFile_ButHeaderIncorrect_ShouldReturnFalse() {
        try {
            StateCensusAnalyser<StateCensus> censusAnalyser = new StateCensusAnalyser<>();
            int count = censusAnalyser.openCsvBuilder(STATECENSUS_CSVFILE);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.INCORRECT_DATA_ISSUE, e.type);
        }
    }

}

