
본 가이드는 [awesome-shopping-finished](https://github.com/cloudsvcdev/awesome-shopping-finished) 에서 설명하는 7개 마이크로서비스 중 awesome-account-service를 예제로 선택하여 ZCP를 활용한 CICD pipeline을 통해 배포하는 방법을 설명합니다. 

Prerequisite: 
* awesome-shopping-finished/kustomize/overlay/rbac/에 있는 DB, MQ 등 backend 서비스 먼저 배포 되어 있다는 가정하에 배포 가능합니다.

# 1. Docker Image Registry 생성 
Docker Image 관리를 위한 저장소 필요하며 [Image Registry 가입 및 사용](https://support.cloudz.co.kr/support/solutions/articles/42000044430-image-registry-%EA%B0%80%EC%9E%85-%EB%B0%8F-%EC%82%AC%EC%9A%A9) 가이드를 참조하여 docker image 저장소를 생성합니다. 

# 2. 소스 저장소 생성
소스 관리를 위해 [소스 저장소 관리](https://support.cloudz.co.kr/support/solutions/articles/42000044426-%EC%86%8C%EC%8A%A4-%EC%A0%80%EC%9E%A5%EC%86%8C-%EA%B4%80%EB%A6%AC#id-%EC%86%8C%EC%8A%A4%EC%A0%80%EC%9E%A5%EC%86%8C%EA%B4%80%EB%A6%AC-%EC%A1%B0%EC%A7%81%EC%97%90%EC%A0%80%EC%9E%A5%EC%86%8C%EC%83%9D%EC%84%B1) 가이드를 참조하여 저장소 생성합니다. 

# 3. CICD pipeline 구성
서비스를 사용하기 위해 ZCP Console 의 사이드 메뉴에서 `DevOps > 빌드 및 배포` 를 클릭합니다.

## Jenkins 폴더에 Credential 생성
Jenkins 에서 빌드, 배포를 수행하기 위해서 아래 2가지 Credential 정보를 추가해야 합니다. 1) 소스를 Checkout 할 Git Repository 의 접속 정보와 2) Docker Image를 Push 하기 위한 Registry 접속 계정 정보입니다.

### Git Credential 생성
좌측 메뉴 중 `Credentials` 를 클릭합니다.
해당 Folder에서 `global` 링크를 클릭합니다.
좌측 메뉴에서 `Add Credentials` 메뉴를 클릭합니다.
`Kind` dropdown 메뉴에서 `Username with password` 를 선택 후 아래와 같이 입력합니다.
* Username: 2번에서 생성한 사용자 계정
* Password: 2번에서 생성한 사용자 패으워드
* ID: GIT_CREDENTIALS 를 입력하고 OK 버튼을 클릭합니다 

### Docker Registry Credential 생성
위에서 설명한 `Git Credential 생성` 방법과 동일합니다. 입력 항목을 아래와 같이 등록하면 됩니다.
* Username: 1번에서 생성한 사용자 계정
* Password: 1번에서 생성한 사용자 패으워드
* ID: HARBOR_CREDENTIALS 를 입력하고 OK 버튼을 클릭합니다 

## Pipeline 구성
Jenkins의 좌측 메뉴의 `New Item` 을 클릭합니다.
* Enter an item name: Application 또는 Job 이름을 입력합니다.
* Pipeline 을 선택해서 OK 버튼을 생성합니다. 

### Git 설정 
* 생성된 pipeline에서 `Advanced Project Options` 탭을 클릭합니다.
* Definition: Pipeline script from SCM 을 선택합니다.
* SCM: GIT으로 선택합니다.
* Repository URL: 1번에서 생성한 소스 저장소 URL 복사해서 입력합니다.
* Credentials: 위에서 생성한 Git Credential 선택합니다. 
* Branches to build: 에 배포 할 소스 코드의 Git branch 를 입력합니다. (본 예제에서는 master branch으로 선택)
* Repository browser: gogs를 선택합니다.
* Script Path: Jenkinsfile 입력합니다. 
* Save 버튼을 클릭합니다. 

### Jenkinsfile 작성
아래의 Pipeline 코드를 복사하여 어플리케이션 소스의 root 에 Jenkinsfile 로 저장합니다.
```
@Library('retort-lib') _
def label = "jenkins-${UUID.randomUUID().toString()}"

def ZCP_USERID='cloudzdb-admin'
def DOCKER_IMAGE='shopping/awesome-account'
def K8S_NAMESPACE='shopping'

timestamps {
    podTemplate(label:label,
        serviceAccount: "zcp-system-sa-${ZCP_USERID}",
        containers: [
            containerTemplate(name: 'maven', image: 'maven:3.5.2-jdk-8-alpine', ttyEnabled: true, command: 'cat'),
            containerTemplate(name: 'docker', image: 'docker:17-dind', ttyEnabled: true, command: 'dockerd-entrypoint.sh', privileged: true),
            containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl:v1.13.6', ttyEnabled: true, command: 'cat')
        ],
        volumes: [
            persistentVolumeClaim(mountPath: '/root/.m2', claimName: 'zcp-jenkins-mvn-repo')
        ]) {

        node(label) {
            stage('SOURCE CHECKOUT') {
                def repo = checkout scm
            }

            stage('BUILD') {
                container('maven') {
                    mavenBuild goal: 'clean package', systemProperties:['maven.repo.local':"/root/.m2/${JOB_NAME}"]
                }
            }

            stage('BUILD DOCKER IMAGE') {
                container('docker') {
                    dockerCmd.build tag: "${HARBOR_REGISTRY}/${DOCKER_IMAGE}:${BUILD_NUMBER}"
                    dockerCmd.push registry: HARBOR_REGISTRY, imageName: DOCKER_IMAGE, imageVersion: BUILD_NUMBER, credentialsId: "HARBOR_CREDENTIALS_SHOPPING"
                }
            }

            stage('DEPLOY') {
                container('kubectl') {
                    kubeCmd.apply file: 'k8s/service.yaml', namespace: K8S_NAMESPACE
                    kubeCmd.apply file: 'k8s/config.yaml', namespace: K8S_NAMESPACE
                    yaml.update file: 'k8s/deploy.yaml', update: ['.spec.template.spec.containers[0].image': "${HARBOR_REGISTRY}/${DOCKER_IMAGE}:${BUILD_NUMBER}"]

                    kubeCmd.apply file: 'k8s/deploy.yaml', wait: 300, recoverOnFail: false, namespace: K8S_NAMESPACE
                }
            }
        }
    }
}

```
위 Jenkinsfile 에서 아래의 내용을 수정하고 저장합니다.
* 4번 라인 ZCP_USERID : 배포담당자의 ZCP Console ID 를 입력합니다.
* 5번 라인 DOCKER_IMAGE : 생성 할 Docker Image 명을 입력합니다.
* 6번 라인 K8S_NAMESPACE: 배포할 kubernetes namespace 명을 입력합니다.

# 4. awesome-account-service 소스를 배포 Pipeline으로 배포 가능하도록 정리
[awesome-shopping-finished](https://../../../tree/master/kustomize)에서 설명한 배포 방식은 kustomize 툴을 사용하여 배포하는 방법을 설명했습니다. ZCP 활용한 pipeline을 활용하여 배포하려면 소스를 구조를 아래와 같이 변경해야 합니다. 
먼저 awesome-shopping-finished의 전체 소스 코드를 로컬에 내려받은 후 다름 설정을 합니다.

## k8s 폴더 생성
awesome-shopping-finished > awesome-account-service 에 deploy.yaml, service.yaml 등 배포를 위한 파일을 저장하시 위해 k8s 폴더를 생성합니다.
awesome-shopping-finished/awesome-account-service에 k8s 폴더 생성합니다. 

## deploy.yaml, service.yaml, config.yaml 생성
awesome-shopping-finished/awesome-account-service/kustomize 로 이동하여 아래 명령어로 컨페이너에 배포하기 위한 deploy.yaml, service.yaml, config.yaml 파일을 추출합니다.

```
$ kustomize build overlay/account >> account.yaml
```

생성된 account.yaml 파일을 열어 아래와 같이 deploy.yaml, service.yaml, config.yaml 파일을 생성하여 awesome-shopping-finished/awesome-account-service/k8s에 복사합니다

deploy.yaml 파일 내용: 
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: account
  name: account-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: account
  template:
    metadata:
      labels:
        app: account
    spec:
      containers:
      - env:
        - name: DB_ENDPOINT
          valueFrom:
            configMapKeyRef:
              key: endpoint
              name: db-config
        - name: DB_PORT
          valueFrom:
            configMapKeyRef:
              key: port
              name: db-config
        - name: DB_NAME
          valueFrom:
            configMapKeyRef:
              key: dbname
              name: db-config
        - name: DB_USERNAME
          valueFrom:
            secretKeyRef:
              key: username
              name: db-secret
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              key: password
              name: db-secret
        - name: MQ_ENDPOINT
          valueFrom:
            configMapKeyRef:
              key: endpoint
              name: mq-config
        - name: MQ_PORT
          valueFrom:
            configMapKeyRef:
              key: port
              name: mq-config
        - name: MQ_USERNAME
          valueFrom:
            secretKeyRef:
              key: username
              name: mq-secret
        - name: MQ_PASSWORD
          valueFrom:
            secretKeyRef:
              key: password
              name: mq-secret
        image: cloudsvcdev/awesome-account-service
        livenessProbe:
          failureThreshold: 2
          httpGet:
            path: /actuator/health
            port: 8180
          initialDelaySeconds: 100
          periodSeconds: 8
          timeoutSeconds: 2
        name: microservice
        readinessProbe:
          failureThreshold: 2
          httpGet:
            path: /actuator/health
            port: 8180
          initialDelaySeconds: 40
          periodSeconds: 3
          timeoutSeconds: 2
      - image: nginx:latest
        name: sidecar-container
      serviceAccountName: config-reader

```

service.yaml 파일 내용:
```yaml
apiVersion: v1
kind: Service
metadata:
  labels:
    app: account
  name: account-app
spec:
  ports:
  - name: http
    port: 80
    protocol: TCP
    targetPort: 8180
  selector:
    app: account
  type: NodePort

```

config.yaml 파일 내용:
```yaml
apiVersion: v1
data:
  application.yaml: "server:\n  port: 8180 # should be configured as variable\nspring:\n
    \ cloud:\n    stream:\n      binders:\n        rabbitmq:\n          type: rabbit\n
    \         environment:\n            spring:\n              rabbitmq:\n                #
    host: awesome-shopping-mq\n                # port: 5672\n                host:
    ${MQ_ENDPOINT}\n                port: ${MQ_PORT}\n                username: ${MQ_USERNAME}\n
    \               password: ${MQ_PASSWORD}\n    kubernetes:\n      reload:\n        enabled:
    true\n        strategy: restart-context\n      secrets:\n        sources: \n          -
    name: db-secret\n          - name: mq-secret\n  datasource:\n    platform: mariadb\n
    \   # url: jdbc:mariadb://my-release-mariadb:3306/awesomeshoppingdb\n    url:
    jdbc:mariadb://${DB_ENDPOINT}:${DB_PORT}/${DB_NAME}\n    username: ${DB_USERNAME}\n
    \   password: ${DB_PASSWORD}\n    driver-class-name: org.mariadb.jdbc.Driver\n
    \   initialization-mode: always\n  h2:\n    console:\n      enabled: false\nmanagement:\n
    \ endpoint:\n    restart:\n      enabled: true\n  health:\n    rabbit:\n      enabled:
    false"
kind: ConfigMap
metadata:
  labels:
    app: account
  name: account-app

```

## Jenkinsfile 복사
3번 (CICD pipeline 구성) 에서 생성한 Jenkins파일을 awesome-account-service의 root 디렉토리인 awesome-shopping-finished/awesome-account-service 폴더에 복사합니다. 

## awesome-account-service 소스 코드 저장소에 push
2번(소스 저장소 생성) 에서 생성한 소스 저장소에 awesome-shopping-finished/awesome-account-service에 있는 소스만 push 합니다. 

# 5. Pipeline 실행
3번(CICD pipeline 구성) 에서 생성한 Pipeline 명을 선택하여 Build Now 를 클릭합니다.

