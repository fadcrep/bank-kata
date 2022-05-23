package com.fadtech.bank.service.mapper.todb;

import com.fadtech.bank.model.Account;
import com.fadtech.bank.repository.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapperToDb {

    /**
     * Instance of AccountMapperToDb.
     */
    public static AccountMapperToDb INSTANCE = Mappers.getMapper(AccountMapperToDb.class);

    /**
     * Map Account to AccountEntity.
     *
     * @param account Account
     * @return AccountEntity
     */
    AccountEntity mapOne(Account account);
}
