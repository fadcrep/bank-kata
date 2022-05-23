package com.fadtech.bank.service.mapper.fromdb;

import com.fadtech.bank.model.Account;
import com.fadtech.bank.repository.entity.AccountEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static com.fadtech.bank.model.AccountType.CURRENT;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountMapperFromDbTest {

    @Test
    public void should_map_account_entity_to_account() {
        //Given
        AccountEntity accountEntity = AccountEntity.builder()
                .id(1L)
                .accountNumber(12145L)
                .balance(BigDecimal.ZERO)
                .accountType(CURRENT)
                .dateOfBalance(Instant.now())
                .build();

        //When
        Account account = AccountMapperFromDb.INSTANCE.mapOne(accountEntity);

        //Then
        assertThat(account).isNotNull();
        assertThat(account.getId()).isEqualTo(accountEntity.getId());
        assertThat(account.getAccountNumber()).isEqualTo(accountEntity.getAccountNumber());
        assertThat(account.getBalance()).isEqualTo(accountEntity.getBalance());
        assertThat(account.getAccountType()).isEqualTo(accountEntity.getAccountType());
        assertThat(account.getDateOfBalance()).isEqualTo(accountEntity.getDateOfBalance());
    }

}
