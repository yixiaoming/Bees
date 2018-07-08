package org.yxm.bees.exception;

public class DataEmptyException extends RuntimeException {

    public DataEmptyException() {
        super("data is empty");
    }
}
