package com.fadtech.bank.service.mapper.todb;

import com.fadtech.bank.model.Account;
import com.fadtech.bank.model.Operation;
import com.fadtech.bank.repository.entity.OperationEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static com.fadtech.bank.model.AccountType.SAVINGS;
import static com.fadtech.bank.model.OperationType.DEPOSIT;
import static org.assertj.core.api.Assertions.assertThat;

public class OperationMapperToDbTest {

    @Test
    public void should_map_operation_to_operation_entity() {

        // Given
        Account account = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(100.0))
                .accountType(SAVINGS)
                .accountNumber(123456789L)
                .dateOfBalance(Instant.now())
                .build();

        Operation operation = Operation.builder()
                .id(1L)
                .account(account)
                .amount(BigDecimal.valueOf(100.0))
                .date(Instant.now())
                .type(DEPOSIT)
                .balance(BigDecimal.valueOf(200.0))
                .build();
        // When
        OperationEntity operationEntity = OperationMapperToDb.INSTANCE.mapOne(operation);

        // Then
        assertThat(operationEntity).isNotNull();
        assertThat(operationEntity.getId()).isEqualTo(operation.getId());
        assertThat(operationEntity.getAccount().getId()).isEqualTo(operation.getAccount().getId());
        assertThat(operationEntity.getAmount()).isEqualTo(operation.getAmount());
        assertThat(operationEntity.getDate()).isEqualTo(operation.getDate());
        assertThat(operationEntity.getType()).isEqualTo(operation.getType());
        assertThat(operationEntity.getBalance()).isEqualTo(operation.getBalance());
    }

}

