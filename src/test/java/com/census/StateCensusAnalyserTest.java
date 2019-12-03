package com.census;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class StateCensusAnalyserTest {
    public static final String STATECENSUS_CSVFILE = "/home/admin97/Ayush/github/MyCensusAnalyser/src/main/resources/StateCensusData.csv";

    @Test
    public void GivenTheStateCodesCsvFile_IfHasCorrectNumberOfRecords_ShouldReturnTrue() throws IOException {
        StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
        try {
            int count = stateCensusAnalyser.openCsvBuilder(STATECENSUS_CSVFILE, StateCensus.class);
            System.out.println(count);
            Assert.assertEquals(29, count);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


}
