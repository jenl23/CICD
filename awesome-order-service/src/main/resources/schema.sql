--sequence
create sequence if not exists event_seq;
create sequence if not exists order_seq;

-- orders
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
  id bigint NOT NULL
  ,accountId bigint  NOT NULL
  ,paymentId bigint  NOT NULL
  ,accountInfo text  NOT NULL
  ,productsInfo text  NOT NULL
  ,paymentInfo text  NOT NULL
  ,paid varchar(255) NOT NULL
  ,status varchar(255) NOT NULL 
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);

-- order_events
DROP TABLE IF EXISTS order_events;
CREATE TABLE order_events (
  id bigint NOT NULL
  ,domain varchar(255) NOT NULL
  ,orderId bigint  NOT NULL
  ,eventType varchar(255)  NOT NULL
  ,payload text NOT NULL
  ,txId varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);