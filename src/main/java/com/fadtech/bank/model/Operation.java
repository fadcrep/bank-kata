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
 * class used to manage operation
 */
public class Operation implements Comparable<Operation> {
    private Long id;
    private OperationType type;
    private BigDecimal amount;
    private Instant date;
    private BigDecimal balance;
    private Account account;

    @Override
    public int compareTo(Operation otherOperation) {

        if (otherOperation == this) {
            return 0;
        }

        if (null == otherOperation) {
            return 1;
        }

        Instant otherDate = otherOperation.getDate();


        if (otherDate == date) {
            return 0;
        }

        if (null == date) {
            return -1;
        }

        if (null == otherDate) {
            return 1;
        }


        if (date.isBefore(otherDate)) {
            return -1;
        }

        return 1;

    }
}
