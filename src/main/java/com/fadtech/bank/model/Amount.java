package com.fadtech.bank.model;

import com.fadtech.bank.exception.AmountMustBePositiveException;
import com.fadtech.bank.utils.Message;

import java.math.BigDecimal;


/**
 * The Class Amount.
 * description: Amount is a wrapper class for BigDecimal.
 */
public class Amount {


    private final BigDecimal value;

    public Amount(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new AmountMustBePositiveException(Message.VALUE_MUST_BE_POSITIVE_ERROR_MESSAGE);
        }

        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
