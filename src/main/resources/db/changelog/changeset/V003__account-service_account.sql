ALTER TABLE account ADD COLUMN balance_id BIGINT;
ALTER TABLE account ADD CONSTRAINT fk_balance FOREIGN KEY (balance_id) REFERENCES balance (id)