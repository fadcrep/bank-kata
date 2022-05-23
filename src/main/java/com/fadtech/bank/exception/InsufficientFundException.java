package com.fadtech.bank.exception;

/**
 * The Class InsufficientFundException.
 * description: InsufficientFundException is thrown when the account has insufficient funds for a withdrawal.
 */
public class InsufficientFundException extends BusinessException {

    public InsufficientFundException(String message) {
        super(message);
    }
}
