package com.fadtech.bank.service;

import com.fadtech.bank.exception.AccountNotFoundException;
import com.fadtech.bank.exception.InsufficientFundException;
import com.fadtech.bank.model.Account;
import com.fadtech.bank.model.Amount;
import com.fadtech.bank.model.Operation;
import com.fadtech.bank.model.OperationType;
import com.fadtech.bank.repository.AccountRepository;
import com.fadtech.bank.repository.OperationRepository;
import com.fadtech.bank.repository.entity.AccountEntity;
import com.fadtech.bank.repository.entity.OperationEntity;
import com.fadtech.bank.service.mapper.fromdb.AccountMapperFromDb;
import com.fadtech.bank.service.mapper.fromdb.OperationMapperFromDb;
import com.fadtech.bank.service.mapper.todb.AccountMapperToDb;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static com.fadtech.bank.model.OperationType.DEPOSIT;
import static com.fadtech.bank.model.OperationType.WITHDRAWAL;
import static com.fadtech.bank.utils.Message.ACCOUNT_NOT_FOUND_ERROR_MESSAGE;
import static com.fadtech.bank.utils.Message.INSUFFICIENT_FUNDS_ERROR_MESSAGE;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.reverseOrder;


/**
 * The Class OperationService
 * description: this class is responsible for the business logic of the operation
 */

@Service
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationRepository operationRepository;

    /**
     * @param accountId
     * @param operationAmount
     * @return
     * @throws AccountNotFoundException
     * @throws InsufficientFundException
     */
    public Operation withdrawal(Long accountId, Amount operationAmount) throws AccountNotFoundException,
            InsufficientFundException {

        // 1- Check if account exist and balance allow the operation
        Account account = validateWithdraw(accountId, operationAmount);

        // 2- Create the operation to save
        OperationEntity operationEntityToBeSaved =
                buildWithDrawOperation(operationAmount, account);

        // 3- Save the operation into DB
        OperationEntity operationEntitySaved = operationRepository.save(operationEntityToBeSaved);

        // 4- Mapping from db
        Operation operationSaved = OperationMapperFromDb.INSTANCE.mapOne(operationEntitySaved);

        // 5- Update Balance
        Account accountSaved = updateBalance(account, operationSaved);

        //6- set the new Account information
        operationSaved.setAccount(accountSaved);

        return operationSaved;
    }

    /**
     * @param accountId
     * @param operationAmount
     * @return
     * @throws AccountNotFoundException
     * @throws InsufficientFundException
     */
    public Operation deposit(Long accountId, Amount operationAmount) throws AccountNotFoundException,
            InsufficientFundException {
        // 1- Check if account exist and balance allow the operation
        Account account = validateDeposit(accountId);

        // 2- Create the operation to save
        OperationEntity operationEntityToBeSaved =
                buildDepositOperation(operationAmount, account);

        // 3- Save the operation into DB
        OperationEntity operationEntitySaved = operationRepository.save(operationEntityToBeSaved);

        // 4- Mapping from db
        Operation operationSaved = OperationMapperFromDb.INSTANCE.mapOne(operationEntitySaved);

        // 5- Update Balance
        Account accountSaved = updateBalance(account, operationSaved);

        //6- set the new Account information
        operationSaved.setAccount(accountSaved);

        return operationSaved;
    }

    /**
     * @param accountId
     * @return
     * @throws AccountNotFoundException
     */
    public List<Operation> getOperationsByAccountId(Long accountId) throws AccountNotFoundException {
        Account account = getAccountById(accountId);
        List<OperationEntity> operationEntities = (List<OperationEntity>) operationRepository.findAllByAccountId(accountId);
        List<Operation> operations = OperationMapperFromDb.INSTANCE.mapMany(operationEntities);
        Collections.sort(operations, reverseOrder());
        return operations;
    }

    /**
     * Update the account balance
     *
     * @param account   Account on which we operate
     * @param operation the operation : withdrawal or deposit
     */
    Account updateBalance(Account account, Operation operation) {
        // 1- retrieve the most recent operation
        OperationEntity previousOperation = operationRepository.findFirstBefore(operation.getId());

        // 2-  get the previous balance from the last operation
        BigDecimal previousBalance = previousOperation == null ? ZERO : previousOperation.getBalance();

        // 3- calculate the new balance
        BigDecimal newBalance = calculateBalance(operation.getType(), previousBalance, operation.getAmount());

        // 4- Update the account balance
        account.setBalance(newBalance);

        // 5- set Date of balance
        account.setDateOfBalance(Instant.now());

        // 6- Mapping to db
        AccountEntity accountEntity = AccountMapperToDb.INSTANCE.mapOne(account);

        // 7- Persist into db
        return AccountMapperFromDb.INSTANCE.mapOne(accountRepository.save(accountEntity));
    }

    /**
     * Calculate the new Balance
     *
     * @param operationType   Type of the operation : withdraw or deposit
     * @param previousBalance Balance of the previous operation
     * @param operationAmount Our operation amount
     * @return
     */
    BigDecimal calculateBalance(OperationType operationType, BigDecimal previousBalance, BigDecimal operationAmount) {
        if (DEPOSIT == operationType) {
            return previousBalance.add(operationAmount);
        } else {
            return previousBalance.subtract(operationAmount);
        }
    }

    /**
     * @param accountId
     * @return
     */
    Account validateDeposit(Long accountId) throws AccountNotFoundException {
        return getAccountById(accountId);
    }

    /**
     * @param accountId
     * @param operationAmount
     * @return
     */
    Account validateWithdraw(Long accountId, Amount operationAmount) {
        Account account = getAccountById(accountId);

        if (account.getBalance().subtract(operationAmount.getValue()).compareTo(ZERO) < 0) {
            throw new InsufficientFundException(INSUFFICIENT_FUNDS_ERROR_MESSAGE);
        }

        return account;

    }

    /**
     * @param accountId
     * @return
     */
    private Account getAccountById(Long accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId).orElseThrow(() ->
                new AccountNotFoundException(ACCOUNT_NOT_FOUND_ERROR_MESSAGE));
        return AccountMapperFromDb.INSTANCE.mapOne(accountEntity);
    }

    /**
     * @param operationAmount
     * @param account
     * @return
     */
    private OperationEntity buildWithDrawOperation(Amount operationAmount, Account account) {
        return OperationEntity.builder()
                .type(WITHDRAWAL)
                .amount(operationAmount.getValue())
                .date(Instant.now())
                .balance(account.getBalance().subtract(operationAmount.getValue()))
                .account(AccountMapperToDb.INSTANCE.mapOne(account))
                .build();
    }

    /**
     * @param operationAmount
     * @param account
     * @return
     */
    private OperationEntity buildDepositOperation(Amount operationAmount, Account account) {
        return OperationEntity.builder()
                .type(DEPOSIT)
                .amount(operationAmount.getValue())
                .date(Instant.now())
                .balance(account.getBalance().add(operationAmount.getValue()))
                .account(AccountMapperToDb.INSTANCE.mapOne(account))
                .build();
    }
}
