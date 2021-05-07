--sequence
select event_seq.nextval;

INSERT INTO carts
(accountId,productId,productActive,productQuantity,productInfo,createdAt)
VALUES
(1
,1
,'active'
,1
,'{"id":1,"name":"Esprit Ruffle Shirt","categoryName":"women","amount":100,"originalPrice":80000,"salePercentage":5,"salePrice":4000,"resultPrice":76000,"img":"images/product-01.jpg"}'
,NOW());

INSERT INTO cart_events
(id,cartId,domain,eventType,payload,txId,createdAt)
VALUES
(1,1,'cart','CartProductAdded'
,'{"id":1,"accountId":1,"productId":1,"productActive":"active","productQuantity":1,"productInfo":{"id":1,"name":"Esprit Ruffle Shirt","categoryName":"women","amount":100,"originalPrice":80000,"salePercentage":5,"salePrice":4000,"resultPrice":76000,"img":"images/product-01.jpg"}}'
,'tx123123124'
,NOW());