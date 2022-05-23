package com.fadtech.bank;

import com.fadtech.bank.model.Account;
import com.fadtech.bank.model.AccountType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    @Test
    public void should_instantiate_balance() {
        Account account = Account.builder().id(12L).accountType(AccountType.SAVINGS).balance(BigDecimal.ZERO)
                .accountNumber(1256L).dateOfBalance(Instant.now()).build();
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ZERO);

    }
}
