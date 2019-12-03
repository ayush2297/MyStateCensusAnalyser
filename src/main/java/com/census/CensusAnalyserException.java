package com.census;

public class CensusAnalyserException extends Exception{
    enum CensusExceptionType{
        NO_SUCH_FILE, INCORRECT_DATA_ISSUE, SOME_OTHER_IO_EXCEPTION, DELIMITER_ISSUE,  NO_SUCH_CLASS
    }
    CensusExceptionType type;
    private String message;

    public CensusAnalyserException() {
    }

    public CensusAnalyserException(CensusExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
