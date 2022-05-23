package com.fadtech.bank.exception;

/**
 * The Class AmountMustBePositiveException.
 * description: AmountMustBePositiveException is thrown when the amount is not positive
 */

public class AmountMustBePositiveException extends BusinessException {

    public AmountMustBePositiveException(String message) {
        super(message);
    }
}
