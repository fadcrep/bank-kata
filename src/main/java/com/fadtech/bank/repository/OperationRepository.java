package com.fadtech.bank.repository;

import com.fadtech.bank.repository.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Interface used to store operation data in db with jpa
 */
@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Long> {

    @Query(value = "SELECT * FROM operation o WHERE o.id != :id  ORDER BY id DESC  LIMIT 1",
            nativeQuery = true)
    OperationEntity findFirstBefore(@PathVariable() Long id);

    // find all operations with the given account id
    @Query(value = "SELECT * FROM operation o WHERE o.account_id = :accountId ORDER BY o.operation_date DESC",
            nativeQuery = true)
    Iterable<OperationEntity> findAllByAccountId(@PathVariable() Long accountId);


}
