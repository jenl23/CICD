--sequence
create sequence if not exists event_seq;

-- accounts
DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts (
  id bigint NOT NULL AUTO_INCREMENT
  ,username varchar(255)  NOT NULL
  ,name varchar(255)  NOT NULL
  ,password varchar(255)  NOT NULL
  ,mobile varchar(255)  NOT NULL
  ,address varchar(255)  NOT NULL
  ,scope varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (id)
);

-- account_events
DROP TABLE IF EXISTS account_events;
CREATE TABLE account_events (
  id bigint NOT NULL
  ,domain varchar(255) NOT NULL
  ,accountId bigint  NOT NULL
  ,eventType varchar(255)  NOT NULL
  ,payload text NOT NULL
  ,txId varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (id)
);

-- account_banks
DROP TABLE IF EXISTS account_banks;
CREATE TABLE account_banks (
  id bigint NOT NULL AUTO_INCREMENT
  ,accountId bigint NOT NULL
  ,bankName varchar(255) NOT NULL
  ,bankNumber varchar(255) NOT NULL
  ,active varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);

-- account_bank_events
DROP TABLE IF EXISTS account_bank_events;
CREATE TABLE account_bank_events (
  id bigint NOT NULL 
  ,domain varchar(255) NOT NULL
  ,accountBankId bigint  NOT NULL
  ,eventType varchar(255)  NOT NULL
  ,payload text NOT NULL
  ,txId varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);