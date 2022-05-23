package com.fadtech.bank.service.mapper.fromdb;

import com.fadtech.bank.model.Account;
import com.fadtech.bank.model.Account.AccountBuilder;
import com.fadtech.bank.repository.entity.AccountEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-23T05:20:36+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class AccountMapperFromDbImpl implements AccountMapperFromDb {

    @Override
    public Account mapOne(AccountEntity accountEntity) {
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
