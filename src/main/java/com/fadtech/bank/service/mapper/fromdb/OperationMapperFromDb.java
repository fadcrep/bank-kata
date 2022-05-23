package com.fadtech.bank.service.mapper.fromdb;

import com.fadtech.bank.model.Operation;
import com.fadtech.bank.repository.entity.OperationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationMapperFromDb {

    /**
     * Instance of {@link OperationMapperFromDb}
     */
    public static OperationMapperFromDb INSTANCE = Mappers.getMapper(OperationMapperFromDb.class);

    /**
     * Map {@link OperationEntity} to {@link Operation}
     *
     * @param operationEntity
     * @return Operation
     */
    Operation mapOne(OperationEntity operationEntity);


    /**
     * @param operationEnties
     * @return
     */
    List<Operation> mapMany(Collection<OperationEntity> operationEnties);
}
