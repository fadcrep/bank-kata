package com.fadtech.bank.service.mapper.fromdb;

import com.fadtech.bank.model.Operation;
import com.fadtech.bank.repository.entity.AccountEntity;
import com.fadtech.bank.repository.entity.OperationEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static com.fadtech.bank.model.AccountType.CURRENT;
import static com.fadtech.bank.model.OperationType.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;

public class OperationMapperFromDbTest {

    @Test
    public void should_map_operation_entity_to_operation() {

        // Given
        AccountEntity accountEntity = AccountEntity.builder()
                .id(1L)
                .accountNumber(12145L)
                .balance(BigDecimal.ZERO)
                .accountType(CURRENT)
                .dateOfBalance(Instant.now())
                .build();

        OperationEntity operationEntity = OperationEntity.builder()
                .id(123L)
                .account(accountEntity)
                .amount(BigDecimal.valueOf(546.00))
                .balance(BigDecimal.valueOf(100))
                .type(WITHDRAWAL)
                .date(Instant.now())
                .build();

        // When
        Operation operation = OperationMapperFromDb.INSTANCE.mapOne(operationEntity);

        // Then
        assertThat(operation).isNotNull();
        assertThat(operation.getId()).isEqualTo(operationEntity.getId());
        assertThat(operation.getAccount()).isNotNull();
        assertThat(operation.getAccount().getId()).isEqualTo(operationEntity.getAccount().getId());
        assertThat(operation.getAccount().getAccountNumber()).isEqualTo(operationEntity.getAccount().getAccountNumber());
        assertThat(operation.getAccount().getBalance()).isEqualTo(operationEntity.getAccount().getBalance());
        assertThat(operation.getAccount().getAccountType()).isEqualTo(operationEntity.getAccount().getAccountType());
        assertThat(operation.getAccount().getDateOfBalance()).isEqualTo(operationEntity.getAccount().getDateOfBalance());
        assertThat(operation.getAmount()).isEqualTo(operationEntity.getAmount());
        assertThat(operation.getBalance()).isEqualTo(operationEntity.getBalance());
        assertThat(operation.getType()).isEqualTo(operationEntity.getType());
        assertThat(operation.getDate()).isEqualTo(operationEntity.getDate());
    }

    @Test
    public void should_map_many_operation_entity_to_many_operation() {
        // Given
        AccountEntity accountEntity = AccountEntity.builder()
                .id(1L)
                .accountNumber(12145L)
                .balance(BigDecimal.ZERO)
                .accountType(CURRENT)
                .dateOfBalance(Instant.now())
                .build();

        OperationEntity withdrawal = OperationEntity.builder()
                .id(123L)
                .account(accountEntity)
                .amount(BigDecimal.valueOf(546.00))
                .balance(BigDecimal.valueOf(100))
                .type(WITHDRAWAL)
                .date(Instant.now())
                .build();

        OperationEntity deposit = OperationEntity.builder()
                .id(123L)
                .account(accountEntity)
                .amount(BigDecimal.valueOf(546.00))
                .balance(BigDecimal.valueOf(100))
                .type(WITHDRAWAL)
                .date(Instant.now())
                .build();

        List<OperationEntity> operationEntities = Arrays.asList(withdrawal, deposit);

        // When
        List<Operation> operations = OperationMapperFromDb.INSTANCE.mapMany(operationEntities);

        // Then
        assertThat(operations).size().isEqualTo(2);
        assertThat(operations.get(0).getId()).isEqualTo(withdrawal.getId());
        assertThat(operations.get(0).getAccount()).isNotNull();
        assertThat(operations.get(0).getAccount().getId()).isEqualTo(withdrawal.getAccount().getId());
        assertThat(operations.get(0).getAccount().getAccountNumber()).isEqualTo(withdrawal.getAccount().getAccountNumber());
        assertThat(operations.get(0).getAccount().getBalance()).isEqualTo(withdrawal.getAccount().getBalance());
        assertThat(operations.get(0).getAccount().getAccountType()).isEqualTo(withdrawal.getAccount().getAccountType());
        assertThat(operations.get(0).getAccount().getDateOfBalance()).isEqualTo(withdrawal.getAccount().getDateOfBalance());
        assertThat(operations.get(0).getAmount()).isEqualTo(withdrawal.getAmount());
        assertThat(operations.get(0).getBalance()).isEqualTo(withdrawal.getBalance());
        assertThat(operations.get(0).getType()).isEqualTo(withdrawal.getType());
        assertThat(operations.get(0).getDate()).isEqualTo(withdrawal.getDate());
    }


}
