package com.platform.accountservice.exceptions;

public class NullPasswordException extends NullPointerException {
    public NullPasswordException(String s) {
        super(s);
    }
}