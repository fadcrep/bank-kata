package com.fadtech.bank.service.mapper.fromdb;

import com.fadtech.bank.model.Account;
import com.fadtech.bank.repository.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapperFromDb {

    /**
     * Instance of {@link AccountMapperFromDb}
     */
    public static AccountMapperFromDb INSTANCE = Mappers.getMapper(AccountMapperFromDb.class);

    /**
     * Maps the entity to the model.
     *
     * @param accountEntity The entity to map.
     * @return Account
     */
    Account mapOne(AccountEntity accountEntity);
}
