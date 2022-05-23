package com.fadtech.bank.service.mapper.todb;

import com.fadtech.bank.model.Account;
import com.fadtech.bank.model.Operation;
import com.fadtech.bank.repository.entity.AccountEntity;
import com.fadtech.bank.repository.entity.AccountEntity.AccountEntityBuilder;
import com.fadtech.bank.repository.entity.OperationEntity;
import com.fadtech.bank.repository.entity.OperationEntity.OperationEntityBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-23T05:20:36+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class OperationMapperToDbImpl implements OperationMapperToDb {

    @Override
    public OperationEntity mapOne(Operation operation) {
        if ( operation == null ) {
            return null;
        }

        OperationEntityBuilder operationEntity = OperationEntity.builder();

        operationEntity.id( operation.getId() );
        operationEntity.type( operation.getType() );
        operationEntity.amount( operation.getAmount() );
        operationEntity.date( operation.getDate() );
        operationEntity.balance( operation.getBalance() );
        operationEntity.account( accountToAccountEntity( operation.getAccount() ) );

        return operationEntity.build();
    }

    protected AccountEntity accountToAccountEntity(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountEntityBuilder accountEntity = AccountEntity.builder();

        accountEntity.id( account.getId() );
        accountEntity.accountNumber( account.getAccountNumber() );
        accountEntity.balance( account.getBalance() );
        accountEntity.accountType( account.getAccountType() );
        accountEntity.dateOfBalance( account.getDateOfBalance() );

        return accountEntity.build();
    }
}
