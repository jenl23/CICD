# BFF 애플리케이션

기존 shopping 애플리케이션은 인프라 플랫폼 내지 PaaS 레벨에서의 자체적인 Load Balancing 및 Routing을 제공하지 않았으므로 API Gateway를 애플리케이션으로서 별도로 구성하여 라우팅 설정을 수행할 수 밖에 없었다. 

Kubernetes는 기존 Netflix OSS 가 해결하고자 하였던 문제들의 상당수를 플랫폼 레벨로 끌어내렸으며, 따라서 기존의 패턴을 as-is 로 그대로 적용하는것은 불필요한 자원 소모가 될 수도 있다. 별도의 API Gateway 애플리케이션의 구성이 그 예이다. awesome-shopping의 apigateway 서비스는 라우팅 기능과 추가헤더 생성 기능이 각기 설정과 코드로서 들어가 있으나, 이들은 BFF로 통합이 가능한 영역이다.

1. eureka는 client side service discovery를 구현한 기능으로, 과거에는 개별 애플리케이션들과 eureka 애플리케이션 간의 상호 인식을 통해 서비스를 등록하고, user-facing 애플리케이션은 이 eureka앱을 바라보는 방식으로 마이크로서비스들의 scaling에 대비해야 했다. k8s에서는 user-facing 애플리케이션이 직접 Service의 이름을 이용해서 내부 서비스를 호출하면 되므로 라우팅 기능을 보유한 별도 애플리케이션이 필요치 않다
2. API Gateway가 필터를 통해 request에 대한 pre-post processing을 해야 한다면 이는 user-facing 애플리케이션에서 backend 서비스 호출 전 수행해도 무방하다.

상기 이유로 awesome-shopping의 bff 서비스는 기존의 awesome-apigateway-service의 기능을 흡수하여 API Gateway의 TXID 생성 기능을 직접 구현하고 있으며, 라우팅 역시 `spring-cloud-gateway` 를 통해 수행할 수 있도록 하고 있다.

## 로컬 구동

kubernetes 환경에 awesome-shopping 서비스들이 배포되어 있다면 `kubefwd` 를 사용하여 편리하게 쇼핑 서비스들을 로컬 환경에 노출시킬 수 있으며, BFF 개발 테스트를 편리하게 수행할 수 있다. 

```
$ sudo kubefwd svc -n awesome-shopping
Password:
INFO[14:21:51]  _          _           __             _     
INFO[14:21:51] | | ___   _| |__   ___ / _|_      ____| |    
INFO[14:21:51] | |/ / | | | '_ \ / _ \ |_\ \ /\ / / _  |    
INFO[14:21:51] |   <| |_| | |_) |  __/  _|\ V  V / (_| |    
INFO[14:21:51] |_|\_\\__,_|_.__/ \___|_|   \_/\_/ \__,_|    
INFO[14:21:51]                                              
INFO[14:21:51] Version 1.13.0                               
INFO[14:21:51] https://github.com/txn2/kubefwd              
INFO[14:21:51]                                              
INFO[14:21:51] Press [Ctrl-C] to stop forwarding.           
INFO[14:21:51] 'cat /etc/hosts' to see all host entries.    
INFO[14:21:51] Loaded hosts file /etc/hosts                 
INFO[14:21:51] Hostfile management: Backing up your original hosts file /etc/hosts to /Users/sanghoonhan/hosts.original 
INFO[14:21:51] Forwarding: awesome-mq-rabbitmq:4369 to pod awesome-mq-rabbitmq-0:4369 
INFO[14:21:51] Forwarding: awesome-mq-rabbitmq:5672 to pod awesome-mq-rabbitmq-0:5672 
INFO[14:21:51] Forwarding: awesome-mq-rabbitmq:25672 to pod awesome-mq-rabbitmq-0:25672 
INFO[14:21:51] Forwarding: awesome-mq-rabbitmq:15672 to pod awesome-mq-rabbitmq-0:15672 
INFO[14:21:51] Forwarding: awesome-db-mariadb-slave:3306 to pod awesome-db-mariadb-slave-0:3306 
INFO[14:21:51] Forwarding: bff-app:80 to pod bff-app-6df7bf5489-vtcnt:8091 
INFO[14:21:51] Forwarding: order-app:80 to pod order-app-d9f56bd85-7l7x2:8180 
INFO[14:21:52] Forwarding: payment-app:80 to pod payment-app-6ff45c56f-gk2hx:8180 
INFO[14:21:53] Forwarding: awesome-mq-rabbitmq-headless:4369 to pod awesome-mq-rabbitmq-0:4369 
INFO[14:21:53] Forwarding: awesome-mq-rabbitmq-headless:5672 to pod awesome-mq-rabbitmq-0:5672 
INFO[14:21:53] Forwarding: awesome-mq-rabbitmq-headless:25672 to pod awesome-mq-rabbitmq-0:25672 
INFO[14:21:53] Forwarding: awesome-mq-rabbitmq-headless:15672 to pod awesome-mq-rabbitmq-0:15672 
INFO[14:21:53] Forwarding: awesome-mq-rabbitmq-0.awesome-mq-rabbitmq-headless:4369 to pod awesome-mq-rabbitmq-0:4369 
INFO[14:21:53] Forwarding: awesome-mq-rabbitmq-0.awesome-mq-rabbitmq-headless:5672 to pod awesome-mq-rabbitmq-0:5672 
INFO[14:21:53] Forwarding: awesome-mq-rabbitmq-0.awesome-mq-rabbitmq-headless:25672 to pod awesome-mq-rabbitmq-0:25672 
INFO[14:21:53] Forwarding: awesome-mq-rabbitmq-0.awesome-mq-rabbitmq-headless:15672 to pod awesome-mq-rabbitmq-0:15672 
INFO[14:21:55] Forwarding: awesome-db-mariadb:3306 to pod awesome-db-mariadb-master-0:3306 
INFO[14:21:55] Forwarding: account-app:80 to pod account-app-548dbff6cc-vntpw:8180 
INFO[14:21:56] Forwarding: apigateway-app:80 to pod apigateway-app-57d6488d88-hsh4m:8090 
INFO[14:21:56] Forwarding: cart-app:80 to pod cart-app-5d4cc89ddc-qjtz5:8180 
INFO[14:21:57] Forwarding: product-app:80 to pod product-app-9f65c96bf-8csdc:8180 
```

`kubefwd` 를 통해 서비스를 포워딩하고 있다면 아래와 같이 `application-localroute.yml` profile을 이용하여 BFF 서비스를 로컬에서 구동할 수 있다. 애플리케이션이 수정되면 `spring-boot-devtools`에 의해 자동으로 서비스가 재시작된다.

```
$ mvn spring-boot:run -Dspring-boot.run.profiles=localroute
```