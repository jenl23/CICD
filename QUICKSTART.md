# local환경에서의 dev 배포 quickstart

실제 Kubernetes 클러스터가 없더라도 로컬환경에 클러스터를 생성하여 배포 테스트를 수행할 수 있다. 

클러스터 생성 및 backing service 생성. 클러스터의 생성은 [kind](https://kind.sigs.k8s.io/) 사용을 가정한다. backing service는 [helm](https://helm.sh/) 을 사용한다. 

```
kind create cluster
helm install awesome-mq bitnami/rabbitmq \
 --set rabbitmq.username=labs,rabbitmq.password=awesome-rabbitmq
helm install awesome-db bitnami/mariadb \
 --set rootUser.password=p455w0rd
```

DB설정

```
MariaDB [(none)]> set global sql_mode='ORACLE';
Query OK, 0 rows affected (0.002 sec)

MariaDB [(none)]> create database awesomeshoppingdb;
Query OK, 1 row affected (0.004 sec)

MariaDB [(none)]> exit
```

kustomize 빌드 결과물 확인방법

```
kubectl kustomize overlay/awesome-bff/dev
kubectl kustomize overlay/awesome-all/dev
...
```

배포

```
kubectl apply -k overlay/awesome-all/dev
kubectl port-forward svc/bff-app-v0-dev 31000:80
```

http://localhost:31000
http://localhost:31000/actuator
http://localhost:31000/actuator/env

스프링 애플리케이션의 서버 포트 및 replicas 변경을 위한 패치는 bff에만 들어가 있으므로 로컬과 같이 자원이 부족한 환경에서 전체 배포 없이 약식으로 테스트하고자 한다면 bff만 배포하고 상단의 actuator 링크를 이용한 테스트를 진행할 수 있다.

```
kubectl apply -k overlay/awesome-bff/dev
kubectl port-forward svc/bff-app-v0-dev 31000:80
```

## 참고

* BFF만 tag가 `latest`가 아닌 `webflux`임
* BFF만 Patch를 적용하여 replicas 설정 및 컨테이너 서비스 포트 변경하는 예시가 들어가 있음
* `overlay/awesome-bff/dev` 에 첨부된 코드에는 간단한 설명을 위한 주석이 달려있다.
* 그 외의 서비스는 image 태그로 `latest`를 사용하고 있다. 포트 변경 역시 반영되어 있지 않다.
* `overlay/*/prod` 에는 임의의 이미지 태그가 적용되어 있는데, 이는 배포 승인 시스템에 의해 임의로 tag가 매겨진다는 가정 하에 작성된 예시이다.

### ConfigMap

1. ConfigMap 생성시 사용되는 file은 반드시 kustomization.yaml 과 동일 레벨 또는 하위 디렉토리에 존재해야 한다. 
2. 위의 특성은 전체 공통 요소를 오버레이 아레가 아닌 베이스에 두게 함. pattern ConfigMap 생성을 위한 kustomization.yaml 은 오버레이의 하위가 아닌 베이스 구성요소로써 config/dev 및 config/prod 에 구성하고 타 overlay에서 bases 로써 참조하도록 하였다. 폴더 구조상으로는 config/dev 폴더 하나이지만, kustomization 빌드 과정에서 이를 base로 삼는 모든 서비스의 개별 ConfigMap으로써 생성이 된다. 

```
$ kubectl get cm
NAME                                       DATA   AGE
account-app-config-v0-dev-dc56465k2h       2      11m
account-pattern-config-v0-dev-thg766t8c7   3      11m
account-system-config-v0-dev-5d6bckt66m    1      11m
bff-app-config-v0-dev-gmft57mgh5           2      11m
bff-pattern-config-v0-dev-h4675ckfkk       3      11m
bff-system-config-v0-dev-md5hfbf8mf        1      11m
...
```

위와 같이 서비스 하나당 app-config(서비스별 전체공통 및 개별설정, application*.yaml 포함), pattern-config(패턴영역 공통설정, application*.yaml 포함), system-config(환경변수 설정, key-value쌍)의 3개의 ConfigMap 이 생성된다. 

`config/dev/application-*.yaml` 을 수정하고서 kustomize 배포를 수정하게되면 모든 deployment가 참조하는 `*-pattern-config-*` 가 변경되므로 전체의 롤링업데이트가 수행된다.

https://github.com/cloudsvcdev/remote-kustomize-config-sample 에 pattern-config 사항을 별도로 commit 하였으며, overlay들은 base로 pattern config용 kustomize를 참조함에 있어서 외부 git repo 경로를 통해 참조할 수 있다. 동일 repo에서 참조하고자 한다면 아래와 같이 참조할 수도 있다.

```yaml
bases:
  - github.com/cloudsvcdev/awesome-shopping-finished/pattern/config/dev
```

### spring.config.location

본 내용은 자바 코드로 `@PropertySource()` 또는 `EnvironmentPostProcessor`를 통한 프로퍼티 참조를 하더라도 참조 순서에 대한 내용만 이해하면 기본적인 원리는 동일하게 적용할 수 있다. base kustomization 에는 다음의 환경변수 설정이 있고 이는 pod의 환경변수로 주입된다.

```yaml
configMapGenerator:
  - name: system-config
    literals:
      - SPRING_CONFIG_LOCATION=classpath:/,classpath:/config/,file:/,file:/pattern/dbaccess.yaml,file:/pattern/messagechannel.yaml,file:/pattern/session.yaml,file:/pattern/cache.yaml,file:/pattern/metric.yaml,file:/pattern/log.yaml,file:/config/,file:/config/application-overlay.yaml
```

볼륨에 마운트되는 properties 파일의 읽는 순서를 정의하며, 전체 공통에 해당되는 패턴용 properties가 먼저 선언이 되어 있고, 관련 설정을 최종적으로 override할 수 있는 오버레이의 properties 파일이 가장 마지막에 선언되어 있다.