# Jenkins 기반의 Pipeline 구성
Jenkins 기반의 CICD Pipeline을 구성하기 위해 awesome-shopping에서는 개발코드에 대한 각각의 Repository와 개발형상정보를 관리하는 Repository로 분리하고 있다. Appl-Modernization-framework에서는 해당 Repository을 1개의 Git Repository로 관리하기 위해 실제 개발/운영환경에서 구성하는 Git Repository 구성과는 차이가 있다.

## awesome-shopping의 템플릿으로 구성시에 개발Repo와 개발형상Repo 구성
완성된 최종 awesome-shopping의 애플리케이션 소스코드와, kustomize 기반의 형상정보 입니다. 
Awesome Shopping의 배포를 위한 형상 Git Repository는 아래와 같이 구성한다.
참고 자료 : https://asf-git.cloudzcp.io/awesome-shopping
```
# 각 마이크로서비스는 별도의 소스코드 관리
{GIT_URL}/awesome-shopping/account-service.git
{GIT_URL}/awesome-shopping/bff-service.git
{GIT_URL}/awesome-shopping/cart-service.git
{GIT_URL}/awesome-shopping/order-service.git
{GIT_URL}/awesome-shopping/payment-service.git
{GIT_URL}/awesome-shopping/product-service.git

# 개발형상 코드에 대한 Repository 관리
{GIT_URL}/awesome-shopping/release-configuration.git
    +/overlay     # CD을 위한 kustomize 기반의 형상 정보
    +/jenkinsfile # Jenkins pipeline에 대한 Jenkinsfile등에 대한 관리
```

### 마이크로서비스 6종 & 개발형상 1종 예시
![스크린샷 2021-03-22 오후 3 53 45](https://user-images.githubusercontent.com/1082462/111951293-d1371c00-8b26-11eb-936e-05ee1186b2a0.png)

### release-configuration 구성
kustomize기반의 개발형상 정보와 jenkins-pipeline 정보를 담아 Repository을 구성한다.
![스크린샷 2021-03-22 오후 3 56 35](https://user-images.githubusercontent.com/1082462/111951591-38ed6700-8b27-11eb-8605-0a72a5a62f52.png)
