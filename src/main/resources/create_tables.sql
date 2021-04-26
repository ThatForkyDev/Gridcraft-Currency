CREATE TABLE IF NOT EXISTS `account_currency` (
    `uuid` BINARY(16) NOT NULL UNIQUE,
    `balance`             DOUBLE NOT NULL,
    PRIMARY KEY (`uuid`)
);