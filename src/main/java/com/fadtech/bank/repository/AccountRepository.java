package com.fadtech.bank.repository;

import com.fadtech.bank.repository.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface used to store account data in db with jpa
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
