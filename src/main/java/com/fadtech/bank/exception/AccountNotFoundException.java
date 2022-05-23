package com.fadtech.bank.exception;

/**
 * The Class AccountNotFoundException.
 * description: AccountNotFoundException is thrown when the account is not found
 */

public class AccountNotFoundException extends BusinessException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
