#!/bin/bash
kubectl delete -k awesome-account-service/overlay/dev &&
kubectl delete -k awesome-bff-service/overlay/dev &&
kubectl delete -k awesome-cart-service/overlay/dev &&
kubectl delete -k awesome-order-service/overlay/dev &&
kubectl delete -k awesome-payment-service/overlay/dev &&
kubectl delete -k awesome-product-service/overlay/dev