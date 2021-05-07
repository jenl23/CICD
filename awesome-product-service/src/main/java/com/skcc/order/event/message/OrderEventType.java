package com.skcc.order.event.message;

public enum OrderEventType {
	OrderCreated
	,OrderCanceled
	,OrderPaymentIdSet
	,OrderPaid
	,OrderCreateFailed
	,OrderCancelFailed
	,OrderPaymentIdSetFailed
	,OrderPayFailed
}
