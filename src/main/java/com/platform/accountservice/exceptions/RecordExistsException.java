package com.platform.accountservice.exceptions;

public class RecordExistsException extends RuntimeException {

    public RecordExistsException(String msg){
        super(msg);
    }
}
