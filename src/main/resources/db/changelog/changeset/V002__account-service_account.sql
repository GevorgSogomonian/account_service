CREATE TABLE IF NOT EXISTS balance
(
    id                    BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_id            BIGINT  NOT NULL,
    authorization_balance DECIMAL NOT NULL,
    current_balance       DECIMAL NOT NULL,
    created_at            timestamptz DEFAULT current_timestamp,
    updated_at            timestamptz DEFAULT current_timestamp,
    version               BIGINT      DEFAULT 1,

    CONSTRAINT balance_account_id_fk FOREIGN KEY (account_id) REFERENCES account (id)
)