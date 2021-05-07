### 빌드를 위한 Maven 설정
awesome-shopping에서 사용하는 MSA Pattern Library의 의존관계 설정을 위해서는 아래와 같이 maven의 settings.xml 설정에 내용이 추가 또는 참조가 필요합니다.
maven 설치 폴더 conf/ 내의 `settings.xml` 파일내의 servers 섹션에 아래와 같이 nexus repository 접속을 위한 credential 설정을 해 주어야 합니다. 맥 또는 리눅스의 경우 `%HOME%/.m2/settings.xml` 파일을 만들어 줍니다.
```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      https://maven.apache.org/xsd/settings-1.0.0.xsd">
  <servers>
    <server>
      <id>skcc-nexus-repository</id>
      <username>skcc-modern-user</username>  
      <password>skccmodernuser</password>  
    </server>
  </servers>
</settings>
```
또는 maven 빌드 명령어에 settings.xml 정보를 읽도록 하여 구성합니다.
```
$ mvn clean package -s settings.xml
```
 * Backing 서비스 Endpoint
   ID와 비밀번호는 VUP Cluster에서 확인 할 것.
```
  * MariaDB 
    - jdbc:mariadb://169.56.162.104:3306/instructor
  * Redis
    - 169.56.171.68:6379
  * RabbitMQ
    - 169.56.171.66:5672
```
# awesome-shopping 완성 소스코드 및 형상정보
완성된 최종 awesome-shopping의 애플리케이션 소스코드와, kustomize 기반의 형상정보 입니다. 
Awesome Shopping 형상정보는 다음과 같이 구성되어 있습니다 
```
/deploy-config
        +/base
        +/pattern
            +/config
                +/dev
/reference
    +/circuitbreaker-resilience4j
    +/gs-circuit-breaker
    +/hpa
    +/logging
    +/monitoring
/awesome-{service} # 각 개발 서비스 별 구조 (account, payment, bff ....)
    +/overlay
....

```

##### rabbitmq Multi-tenancy 환경을 위한 RabbitMQ 설정
사용자별 vhost를 지정해 주어야 합니다. vhost는 cluster의 namespace와 같은 개념입니다.
  - messagechannel.yaml
```
/deploy-config
    +/pattern
        +/config
            +/dev
```
  - 'virtual-host:' 지정 시 username에 명시되는 계정에 admin 권한이 있어야 하며, 패턴 라이브러리는 명시된 vhost를 생성하여 해당 계정에게 할당합니다. (http 프로토콜 사용)
```
spring:
    rabbitmq:
        ...
        virtual-host: edu000  # 수강생 별 구분자
```
