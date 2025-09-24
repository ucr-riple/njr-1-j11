package com.epam.electricalappliance.exeptions;

public class LogicException extends Exception {

    public LogicException() {
    }

    public LogicException(String error) {
        super(error);
    }

    public LogicException(String error, Exception e) {
        super(error,e);
    }

}
