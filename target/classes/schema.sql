DROP TABLE IF EXISTS account, operation;
CREATE TABLE IF NOT EXISTS account
(
    id
    bigint
    NOT
    NULL,
    account_number
    bigint
    NOT
    NULL,
    balance
    decimal
(
    10,
    2
) NOT NULL,
    account_type int NOT NULL,
    date_of_balance timestamp NOT NULL,
    opening_date timestamp NOT NULL,
    PRIMARY KEY
(
    id
)
    );

CREATE TABLE IF NOT EXISTS operation
(
    id
    bigint
    NOT
    NULL,
    account_id
    bigint
    NOT
    NULL,
    amount
    decimal
(
    10,
    2
) NOT NULL,
    type int NOT NULL,
    operation_date timestamp NOT NULL,
    balance decimal
(
    10,
    2
) NOT NULL,
    PRIMARY KEY
(
    id
)
    );

ALTER TABLE operation
    ADD CONSTRAINT fk_operation FOREIGN KEY (account_id) REFERENCES account (id);

INSERT INTO account (id, account_number, balance, account_type, date_of_balance, opening_date)
VALUES (1, 123456789, 0.00, 0, '2017-01-01', '2017-01-01'),
       (2, 987654321, 0.00, 1, '2017-01-01', '2017-01-01');

/*INSERT INTO operation (id, account_id, amount, type, operation_date, balance) VALUES
                                                                                                   (1, 1, 100.00, 0, '2017-01-01', 1000.00),
                                                                                                   (2, 1, 200.00, 0, '2017-01-01', 1200.00),
                                                                                                   (3, 1, 300.00, 1, '2017-01-01', 900.00); */
