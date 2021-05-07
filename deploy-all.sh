#!/bin/bash
cd awesome-account-service &&
./build.sh && cd .. &&
cd awesome-bff-service &&
./build.sh && cd .. &&
cd awesome-cart-service &&
./build.sh && cd .. &&
cd awesome-order-service &&
./build.sh && cd .. &&
cd awesome-payment-service &&
./build.sh && cd .. &&
cd awesome-product-service &&
./build.sh && cd ..