package com.fadtech.bank.service.mapper.todb;

import com.fadtech.bank.model.Account;
import com.fadtech.bank.repository.entity.AccountEntity;
import com.fadtech.bank.repository.entity.AccountEntity.AccountEntityBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-23T05:20:36+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class AccountMapperToDbImpl implements AccountMapperToDb {

    @Override
    public AccountEntity mapOne(Account account) {
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
