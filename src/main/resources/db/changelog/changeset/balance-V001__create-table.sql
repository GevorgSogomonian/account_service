CREATE TABLE balance (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_id BIGINT NOT NULL,
    authorization_amount BIGINT NOT NULL,
    actual_amount BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version_account BIGINT NOT NULL,
    version_balance BIGINT NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account(id)
);