package com.census;


import com.opencsv.bean.CsvBindByName;

import java.util.Comparator;

public class StateCensus implements Comparable<StateCensus>{

    @CsvBindByName(column = "State")
    private String stateName;

    @CsvBindByName(column = "Population",required = true)
    private int population;

    @CsvBindByName(column = "AreaInSqKm")
    private int areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    private int densityPerSqKm;

    public StateCensus() {

    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(int areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public int getDensityPerSqKm() {
        return densityPerSqKm;
    }

    public void setDensityPerSqKm(int densityPerSqKm) {
        this.densityPerSqKm = densityPerSqKm;
    }

    @Override
    public String toString() {
        return  "stateName='" + stateName + '\'' +
                ", population='" + population + '\'' +
                ", areaInSqKm='" + areaInSqKm + '\'' +
                ", densityPerSqKm='" + densityPerSqKm + '\''
                +"\n";
    }

    @Override
    public int compareTo(StateCensus stateCensus) {
        return this.stateName.compareTo(stateCensus.stateName);
    }

    static class StateCensusComparator implements Comparator<StateCensus> {
        public int compare(StateCensus obj1, StateCensus obj2) {
            return obj1.getStateName().compareTo(obj2.getStateName());
        }
    }
}
