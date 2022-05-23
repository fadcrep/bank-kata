package com.fadtech.bank.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
@Builder
/**
 *  Account class
 */
public class Account {
    private Long id;
    private Long accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private Instant dateOfBalance;
}
