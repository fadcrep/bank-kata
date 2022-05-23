package com.fadtech.bank.service.mapper.todb;

import com.fadtech.bank.model.Account;
import com.fadtech.bank.repository.entity.AccountEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static com.fadtech.bank.model.AccountType.SAVINGS;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountMapperToDbTest {
    @Test
    public void should_map_account_to_account_entity() {
        // Given
        Account account = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(100.0))
                .accountType(SAVINGS)
                .accountNumber(123456789L)
                .dateOfBalance(Instant.now())
                .build();
        // When
        AccountEntity accountEntity = AccountMapperToDb.INSTANCE.mapOne(account);

        // Then
        assertThat(accountEntity).isNotNull();
        assertThat(accountEntity.getId()).isEqualTo(account.getId());
        assertThat(accountEntity.getBalance()).isEqualTo(account.getBalance());
        assertThat(accountEntity.getAccountType()).isEqualTo(account.getAccountType());
        assertThat(accountEntity.getAccountNumber()).isEqualTo(account.getAccountNumber());
        assertThat(accountEntity.getDateOfBalance()).isEqualTo(account.getDateOfBalance());

    }

}

