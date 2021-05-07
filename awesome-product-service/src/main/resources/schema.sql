--sequence
create sequence if not exists event_seq;

-- products
DROP TABLE IF EXISTS products;
CREATE TABLE products (
  id bigint NOT NULL AUTO_INCREMENT
  ,name varchar(255)  NOT NULL
  ,categoryId bigint  NOT NULL
  ,active varchar(255)  NOT NULL
  ,amount bigint  NOT NULL
  ,originalPrice bigint  NOT NULL
  ,salePercentage bigint NOT NULL
  ,salePrice bigint NOT NULL
  ,resultPrice bigint NOT NULL
  ,img varchar(255) NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);

-- product_events
DROP TABLE IF EXISTS product_events;
CREATE TABLE product_events (
  id bigint NOT NULL 
  ,domain varchar(255) NOT NULL
  ,productId bigint  NOT NULL
  ,eventType varchar(255)  NOT NULL
  ,payload text NOT NULL
  ,txId varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);

-- categories
DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
  id bigint NOT NULL AUTO_INCREMENT
  ,name varchar(255)  NOT NULL
  ,priority bigint NOT NULL
  ,active varchar(255) NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);

-- category_events
DROP TABLE IF EXISTS category_events;
CREATE TABLE category_events (
  id bigint NOT NULL
  ,domain varchar(255) NOT NULL
  ,categoryId bigint  NOT NULL
  ,eventType varchar(255)  NOT NULL
  ,payload text NOT NULL
  ,txId varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);