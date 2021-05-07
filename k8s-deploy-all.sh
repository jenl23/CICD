#!/bin/bash
kustomize build --load_restrictor none --enable_kyaml=false overlay/v1/dev/account | kubectl apply -f -
kustomize build --load_restrictor none --enable_kyaml=false overlay/v1/dev/bff | kubectl apply -f -
kustomize build --load_restrictor none --enable_kyaml=false overlay/v1/dev/cart | kubectl apply -f -
kustomize build --load_restrictor none --enable_kyaml=false overlay/v1/dev/order | kubectl apply -f -
kustomize build --load_restrictor none --enable_kyaml=false overlay/v1/dev/payment | kubectl apply -f -
kustomize build --load_restrictor none --enable_kyaml=false overlay/v1/dev/product | kubectl apply -f -