--sequence
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select order_seq.nextval;

INSERT INTO orders 
(id, accountId, paymentId, accountInfo, productsInfo, paymentInfo, paid, status, createdAt)
VALUES
(1, 1, 1 
, '{
	"id": 1
	, "username": "lpeterson2a@google.co.uk"
	, "name": "hi"
	, "mobile": "01012345678"
	, "address": "경기도 성남시 분당구"
	, "scope": "customer"
   }'
, '[{ 
	"id": 1
  	, "name": "Esprit Ruffle Shirt"
  	, "categoryName": "women"
  	, "active": "active"
  	, "quantity": 1
  	, "amount": 100
  	, "originalPrice": 80000
  	, "salePercentage": 5
  	, "salePrice": 4000
  	, "resultPrice": 76000
  	, "img": "images/product-01.jpg"
   }]'
, '{"id": 1, "accountId": 1, "paymentMethod": "bank", "paymentDetail1": "kb", "paymentDetail2": "123123123123", "paymentDetail3": "","price": 90000, "paid": "paid"}'
, 'paid'
, 'completed'
, NOW());
  
INSERT INTO order_events 
(id,domain,orderId, eventType, payload, txId, createdAt)
VALUES
(1,'order',1, 'OrderCreated'
, '{
	"id": 1
	,"accountId": 1
	,"paymentId": 1
	, "accountInfo": {
		"id": 1
		, "username": "lpeterson2a@google.co.uk"
		, "name": "hi"
		, "mobile": "01012345678"
		, "address": "경기도 성남시 분당구"
		, "scope": "customer"
	   }
	, "productsInfo": [{ 
		"id": 1
	  	, "name": "Esprit Ruffle Shirt"
	  	, "categoryName": "women"
	  	, "active": "active"
	  	, "quantity": 1
	  	, "amount": 100
	  	, "originalPrice": 80000
	  	, "salePercentage": 5
	  	, "salePrice": 4000
	  	, "resultPrice": 76000
	  	, "img": "images/product-01.jpg"
	   }]
	, "paymentInfo": {"id": 1, "accountId": 1, "paymentMethod": "bank", "paymentDetail1": "kb", "paymentDetail2": "123123123123", "paymentDetail3": "","price": 90000, "paid": "paid"}
	, "paid": "paid"
	, "status": "completed"}'
, 'tx123123129'
, NOW());