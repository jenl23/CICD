--sequence
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;

--account
INSERT INTO accounts 
(id, username, name, password, mobile, address, scope, createdAt)
VALUES
(1, 'lpeterson2a@google.co.uk','하이','et4kBkLz2Kd','01012345678','경기도 성남시 분당구','customer', NOW());

--account_events
INSERT INTO account_events 
(id, domain, accountId, eventType, payload, txId, createdAt)
VALUES
(1, 'account', 1, 'AccountCreated'
, '{
	"id": 1
	, "username": "lpeterson2a@google.co.uk"
	, "name": "하이"
	, "mobile": "01012345678"
	, "address": "경기도 성남시 분당구"
	, "scope": "customer"
   }'
, 'tx123123123'
, NOW());

INSERT INTO account_banks
(accountId,bankName,bankNumber,active,createdAt)
VALUES
(1,'kb','123123123123','active',NOW());

INSERT INTO account_bank_events
(id,accountBankId,domain,eventType,payload,txId,createdAt)
VALUES
(1,1,'accountBank','AccountBankCreated'
,'{"id":1,"accountId":1,"bankName":"kb","bankNumber":"123123123123","active":"active"}'
,'tx123123127'
,NOW());