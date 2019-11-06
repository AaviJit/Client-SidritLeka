package com.platform.accountservice.exceptions;

public class RecordNotFoundException extends RuntimeException {
	public RecordNotFoundException(String msg){
        super(msg);
    }
}
