package com.fadtech.bank.service.mapper.fromdb;

import com.fadtech.bank.model.Account;
import com.fadtech.bank.model.Account.AccountBuilder;
import com.fadtech.bank.model.Operation;
import com.fadtech.bank.model.Operation.OperationBuilder;
import com.fadtech.bank.repository.entity.AccountEntity;
import com.fadtech.bank.repository.entity.OperationEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-23T05:20:37+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class OperationMapperFromDbImpl implements OperationMapperFromDb {

    @Override
    public Operation mapOne(OperationEntity operationEntity) {
        if ( operationEntity == null ) {
            return null;
        }

        OperationBuilder operation = Operation.builder();

        operation.id( operationEntity.getId() );
        operation.type( operationEntity.getType() );
        operation.amount( operationEntity.getAmount() );
        operation.date( operationEntity.getDate() );
        operation.balance( operationEntity.getBalance() );
        operation.account( accountEntityToAccount( operationEntity.getAccount() ) );

        return operation.build();
    }

    @Override
    public List<Operation> mapMany(Collection<OperationEntity> operationEnties) {
        if ( operationEnties == null ) {
            return null;
        }

        List<Operation> list = new ArrayList<Operation>( operationEnties.size() );
        for ( OperationEntity operationEntity : operationEnties ) {
            list.add( mapOne( operationEntity ) );
        }

        return list;
    }

    protected Account accountEntityToAccount(AccountEntity accountEntity) {
        if ( accountEntity == null ) {
            return null;
        }

        AccountBuilder account = Account.builder();

        account.id( accountEntity.getId() );
        account.accountNumber( accountEntity.getAccountNumber() );
        account.balance( accountEntity.getBalance() );
        account.accountType( accountEntity.getAccountType() );
        account.dateOfBalance( accountEntity.getDateOfBalance() );

        return account.build();
    }
}
