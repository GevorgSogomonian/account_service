CREATE TABLE account
(
    id           SERIAL PRIMARY KEY,
    number       VARCHAR(20) NOT NULL,
    owner_id     BIGINT      NOT NULL,
    owner_type   VARCHAR(20) NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    currency     VARCHAR(3)  NOT NULL,
    status       VARCHAR(20) NOT NULL,
    created_at   TIMESTAMP   NOT NULL,
    updated_at   TIMESTAMP   NOT NULL,
    closed_at    TIMESTAMP,
    version      INT         NOT NULL
);