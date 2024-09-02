CREATE TABLE IF NOT EXISTS account_numbers_sequence
(
    account_type    VARCHAR(32) PRIMARY KEY,
    current_counter bigint NOT NULL DEFAULT 1,
    version         BIGINT NOT NULL DEFAULT 0
);

INSERT INTO account_numbers_sequence (account_type, current_counter)
VALUES ('DEBIT', 1),
       ('CREDIT', 1),
       ('DEPOSIT', 1),
       ('CHECKING', 1),
       ('FOREIGN_CURRENCY', 1),
       ('BUSINESS', 1);

CREATE TABLE IF NOT EXISTS free_account_numbers
(
    account_type   VARCHAR(32) NOT NULL,
    account_number bigint      NOT NULL,

    CONSTRAINT free_account_pk PRIMARY KEY (account_type, account_number)

);