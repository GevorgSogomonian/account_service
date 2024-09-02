CREATE TABLE IF NOT EXISTS balance_audit (
    id                       BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_id               BIGINT NOT NULL,
    version_balance          BIGINT NOT NULL,
    authorization_balance    DECIMAL NOT NULL,
    current_balance          DECIMAL NOT NULL,
    transaction_id           BIGINT NOT NULL,
    balance_id               BIGINT NOT NULL,
    created_at               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_balance FOREIGN KEY (balance_id) REFERENCES balance(id),
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE Index idx_version_balance ON balance_audit(version_balance);
CREATE Index idx_created_at ON balance_audit(created_at);