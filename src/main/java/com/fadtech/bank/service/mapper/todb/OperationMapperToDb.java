package com.fadtech.bank.service.mapper.todb;

import com.fadtech.bank.model.Operation;
import com.fadtech.bank.repository.entity.OperationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OperationMapperToDb {

    /**
     * Instance of OperationMapperToDb.
     */
    public static OperationMapperToDb INSTANCE = Mappers.getMapper(OperationMapperToDb.class);

    /**
     * Map operation to operation entity.
     *
     * @param operation
     * @return OperationEntity
     */
    OperationEntity mapOne(Operation operation);
}
