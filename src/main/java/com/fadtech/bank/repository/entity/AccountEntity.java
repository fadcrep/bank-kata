package com.fadtech.bank.repository.entity;

import com.fadtech.bank.model.AccountType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private Instant dateOfBalance;
}
