package com.fadtech.bank.service;

import com.fadtech.bank.exception.AccountNotFoundException;
import com.fadtech.bank.exception.AmountMustBePositiveException;
import com.fadtech.bank.exception.InsufficientFundException;
import com.fadtech.bank.model.Account;
import com.fadtech.bank.model.Amount;
import com.fadtech.bank.model.Operation;
import com.fadtech.bank.repository.AccountRepository;
import com.fadtech.bank.repository.OperationRepository;
import com.fadtech.bank.repository.entity.AccountEntity;
import com.fadtech.bank.repository.entity.OperationEntity;
import com.fadtech.bank.service.mapper.todb.AccountMapperToDb;
import com.fadtech.bank.service.mapper.todb.OperationMapperToDb;
import com.fadtech.bank.utils.Message;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static com.fadtech.bank.model.AccountType.SAVINGS;
import static com.fadtech.bank.model.OperationType.DEPOSIT;
import static com.fadtech.bank.model.OperationType.WITHDRAWAL;
import static com.fadtech.bank.utils.Message.ACCOUNT_NOT_FOUND_ERROR_MESSAGE;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {


    private static OperationService operationService;

    private static OperationRepository operationRepository;

    private static AccountRepository accountRepository;

    // variables used for testing deposit
    private static Account account;
    private static Operation operation;
    private static AccountEntity accountEntity;
    private static OperationEntity operationEntity;
    private static BigDecimal amount;
    private static long ACCOUNT_ID = 123456789L;

    // variables used for testing withdrawal
    private static Account accountForWithdraw;
    private static Operation operationForWithdraw;
    private static AccountEntity accountEntityForWithdraw;
    private static OperationEntity operationEntityForWithdraw;

    private static long ACCOUNT_ID_FOR_WITHDRAW = 1234567890L;

    @BeforeAll
    public static void setUp() {
        amount = BigDecimal.valueOf(100);
        account = Account.builder()
                .id(ACCOUNT_ID)
                .accountNumber(1246L)
                .accountType(SAVINGS)
                .balance(ZERO)
                .dateOfBalance(Instant.now())
                .build();

        operation = Operation.builder()
                .id(123L)
                .account(account)
                .amount(amount)
                .balance(ZERO)
                .type(DEPOSIT)
                .date(Instant.now())
                .build();

        accountForWithdraw = Account.builder()
                .id(23L)
                .accountNumber(ACCOUNT_ID)
                .accountType(SAVINGS)
                .balance(BigDecimal.valueOf(300))
                .dateOfBalance(Instant.now())
                .build();


        operationForWithdraw = Operation.builder()
                .id(123L)
                .account(account)
                .amount(amount)
                .balance(BigDecimal.valueOf(100))
                .type(WITHDRAWAL)
                .date(Instant.now())
                .build();

        // mock account repository and operation repository
        accountRepository = Mockito.mock(AccountRepository.class);
        operationRepository = Mockito.mock(OperationRepository.class);
        operationService = new OperationService(accountRepository, operationRepository);

        // use case for deposit
        operationEntity = OperationMapperToDb.INSTANCE.mapOne(operation);
        accountEntity = AccountMapperToDb.INSTANCE.mapOne(account);

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(accountEntity));

        when(operationRepository.save(Mockito.any())).thenReturn(operationEntity);

        when(operationRepository.findFirstBefore(ACCOUNT_ID)).thenReturn(null);

        // use case for withdrawal
        operationEntityForWithdraw = OperationMapperToDb.INSTANCE.mapOne(operationForWithdraw);
        accountEntityForWithdraw = AccountMapperToDb.INSTANCE.mapOne(accountForWithdraw);

        when(accountRepository.findById(ACCOUNT_ID_FOR_WITHDRAW)).thenReturn(Optional.of(accountEntityForWithdraw));
        when(operationRepository.save(Mockito.any())).thenReturn(operationEntityForWithdraw);
        when(operationRepository.findFirstBefore(ACCOUNT_ID_FOR_WITHDRAW)).thenReturn(operationEntityForWithdraw);

    }


    @Test
    public void should_increment_balance_when_deposit() {

        // Given
        Account expectedAccount = Account.builder()
                .balance(amount)
                .build();
        Operation expectedOperation = Operation.builder()
                .account(expectedAccount)
                .build();

        AccountEntity expectedAccountEntity = AccountMapperToDb.INSTANCE.mapOne(expectedAccount);

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(expectedAccountEntity));

        when(operationService.deposit(ACCOUNT_ID, new Amount(amount))).thenReturn(expectedOperation);

        BigDecimal initialBalance = BigDecimal.ZERO;
        Amount amount = new Amount(BigDecimal.valueOf(100));
        BigDecimal expectedBalance = initialBalance.add(amount.getValue());
        when(operationService.deposit(ACCOUNT_ID, amount)).thenReturn(operation);

        // When
        Operation operation = operationService.deposit(ACCOUNT_ID, amount);


        // Then
        assertEquals(expectedBalance, expectedAccount.getBalance());

    }


    @Test
    public void should_return_operation_decrement_balance_when_withdraw() {

        // Given
        when(accountRepository.findById(ACCOUNT_ID_FOR_WITHDRAW)).thenReturn(Optional.of(accountEntityForWithdraw));

        when(operationRepository.save(operationEntityForWithdraw)).thenReturn(operationEntityForWithdraw);

        Amount amount = new Amount(BigDecimal.valueOf(100));
        BigDecimal expectedBalance = accountEntityForWithdraw.getBalance().subtract(amount.getValue());

        // When
        Operation operation = operationService.withdrawal(ACCOUNT_ID_FOR_WITHDRAW, amount);

        // Then
        assertNotNull(operation);
    }

    @Test
    public void should_throw_account_not_found_when_trying_to_deposit_for_unknown_account_number()
            throws AccountNotFoundException {

        // Given
        when(accountRepository.findById(123567L)).thenReturn(Optional.empty());

        // When
        Exception actualException = assertThrows(AccountNotFoundException.class, () ->
                operationService.deposit(123567L, new Amount(amount)));

        // Then
        assertEquals(actualException.getMessage(), ACCOUNT_NOT_FOUND_ERROR_MESSAGE);
    }

    @Test
    public void should_throw_account_not_found_when_trying_to_withdraw_for_unknown_account_number()
            throws AccountNotFoundException {

        // Given
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

        // When
        Exception actualException = assertThrows(AccountNotFoundException.class, () ->
                operationService.withdrawal(ACCOUNT_ID, new Amount(amount)));

        // Then
        assertEquals(actualException.getMessage(), ACCOUNT_NOT_FOUND_ERROR_MESSAGE);
    }

    @Test
    public void should_throw_insufficient_balance_when_trying_to_withdraw_more_than_balance()
            throws InsufficientFundException {

        // Given
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(accountEntity));

        // When
        Exception actualException = assertThrows(InsufficientFundException.class, () ->
                operationService.withdrawal(ACCOUNT_ID, new Amount(amount)));

        // Then
        assertEquals(actualException.getMessage(), Message.INSUFFICIENT_FUNDS_ERROR_MESSAGE);
    }


    @Test
    public void should_throw_amount_must_be_positive_when_withdraw_with_negative_amount()
            throws AmountMustBePositiveException {

        // Given
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(accountEntity));

        // When
        Exception actualException = assertThrows(AmountMustBePositiveException.class, () ->
                operationService.withdrawal(ACCOUNT_ID, new Amount(BigDecimal.valueOf(-100))));

        // Then
        assertEquals(actualException.getMessage(), Message.VALUE_MUST_BE_POSITIVE_ERROR_MESSAGE);
    }

    @Test
    public void should_throw_amount_must_be_positive_when_deposit_with_negative_amount()
            throws AmountMustBePositiveException {

        // Given
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(accountEntity));

        // When
        Exception actualException = assertThrows(AmountMustBePositiveException.class, () ->
                operationService.deposit(ACCOUNT_ID, new Amount(BigDecimal.valueOf(-100))));

        // Then
        assertEquals(actualException.getMessage(), Message.VALUE_MUST_BE_POSITIVE_ERROR_MESSAGE);
    }

    @Test
    public void should_return_operation_when_deposit_with_positive_amount()
            throws AmountMustBePositiveException {

        // Given
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(accountEntity));
        // When
        Operation savedOperation = operationService.deposit(ACCOUNT_ID, new Amount(amount));

        // Then
        assertNotNull(savedOperation);
    }

    @Test
    public void should_return_operation_when_withdraw_with_positive_amount_when_account_has_sufficient_amount()
            throws InsufficientFundException, AmountMustBePositiveException {

        // Given
        when(accountRepository.findById(ACCOUNT_ID_FOR_WITHDRAW)).thenReturn(Optional.of(accountEntityForWithdraw));
        // When
        Operation operation = operationService.withdrawal(ACCOUNT_ID_FOR_WITHDRAW, new Amount(amount));

        // Then
        assertEquals(amount, operation.getAmount());
        assertEquals(WITHDRAWAL, operation.getType());
    }


    @Test
    public void should_validate_withdrawal_when_account_exist_and_amount_is_positive_and_account_has_suffusient_found() {

        // Given
        when(accountRepository.findById(ACCOUNT_ID_FOR_WITHDRAW)).thenReturn(Optional.of(accountEntityForWithdraw));

        // When
        Account newAccount = operationService.validateWithdraw(ACCOUNT_ID_FOR_WITHDRAW, new Amount(amount));

        // Then
        assertNotNull(newAccount);


    }

    @Test
    public void should_throw_insufficient_fund_when_account_exist_and_amount_is_positive_and_account_has_insufficient_found()
            throws InsufficientFundException {

        // Given
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(accountEntity));

        // When
        Exception actualException = assertThrows(InsufficientFundException.class, () ->
                operationService.validateWithdraw(ACCOUNT_ID, new Amount(amount)));

        // Then
        assertEquals(actualException.getMessage(), Message.INSUFFICIENT_FUNDS_ERROR_MESSAGE);
    }

    @Test
    public void should_throw_amount_must_be_positive_when_account_exist_and_amount_is_negative()
            throws AmountMustBePositiveException {

        // Given
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(accountEntity));

        // When
        Exception actualException = assertThrows(AmountMustBePositiveException.class, () ->
                operationService.validateWithdraw(ACCOUNT_ID, new Amount(BigDecimal.valueOf(-100))));

        // Then
        assertEquals(actualException.getMessage(), Message.VALUE_MUST_BE_POSITIVE_ERROR_MESSAGE);
    }
}
