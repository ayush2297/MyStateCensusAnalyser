package com.census;

public class CensusAnalyserException extends Exception{
    enum CensusExceptionType{
        NO_SUCH_FILE, INCORRECT_DATA_ISSUE, SOME_OTHER_IO_EXCEPTION, INCORRECT_COMPARE_FIELD,
        NO_SUCH_FIELD, NO_SUCH_CLASS, OBJECT_CREATION_ISSUE
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
