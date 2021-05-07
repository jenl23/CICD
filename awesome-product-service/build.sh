#!/bin/bash
./mvnw clean install -DskipTests=true -s ../settings.xml 
docker build . -t factory-registry.cloudzcp.io/edu999/awesome-product-service:0.1.0
docker push factory-registry.cloudzcp.io/edu999/awesome-product-service:0.1.0
