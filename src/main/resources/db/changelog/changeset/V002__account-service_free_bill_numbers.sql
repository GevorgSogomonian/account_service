CREATE TABLE IF NOT EXISTS account_numbers_sequence
(
    account_type varchar(32) PRIMARY KEY,
    current_counter bigint NOT NULL
    );

INSERT INTO account_numbers_sequence (account_type, current_counter)
VALUES ('DEBIT', 1),
       ('CREDIT', 1),
       ('DEPOSIT', 1);

CREATE TABLE IF NOT EXISTS free_account_numbers
(
    account_number char(16) primary key,
    account_type   varchar(32) not null
    );