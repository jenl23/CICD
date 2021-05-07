--sequence
create sequence if not exists event_seq;

-- carts
DROP TABLE IF EXISTS carts;
CREATE TABLE carts (
  id bigint NOT NULL AUTO_INCREMENT
  ,accountId bigint  NOT NULL
  ,productId bigint  NOT NULL
  ,productActive varchar(255) NOT NULL
  ,productQuantity bigint NOT NULL
  -- ,productInfo text NOT NULL
  ,productInfo clob NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);

-- cart_events
DROP TABLE IF EXISTS cart_events;
CREATE TABLE cart_events (
  id bigint NOT NULL
  ,domain varchar(255) NOT NULL
  ,cartId bigint  NOT NULL
  ,eventType varchar(255)  NOT NULL
  -- ,payload text NOT NULL
  ,payload clob NOT NULL
  ,txId varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);