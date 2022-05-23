package com.fadtech.bank.exception;

/**
 *  It is used to handle Invalid Data
 */
public class InvalidDataException extends BusinessException {
    public InvalidDataException(String message) {
        super(message);
    }
}
