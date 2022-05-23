package com.fadtech.bank;

import com.fadtech.bank.exception.AmountMustBePositiveException;
import com.fadtech.bank.model.Amount;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class AmountTest {

    private Amount amount;

    @Test
    public void should_instantiate_amount() {
        amount = new Amount(BigDecimal.TEN);
        assertThat(BigDecimal.TEN, is(amount.getValue()));
    }

    @Test(expected = AmountMustBePositiveException.class)
    public void should_throw_amountMustBePositiveException() {
        new Amount((BigDecimal.TEN).negate());
    }
}
