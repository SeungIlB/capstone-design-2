# 알약 크루



![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.3-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0.27-blue)




> 비대면 진료 서비스를 제공함과 동시에 생성형 AI를 활용하여 즉각적인 질병에 관한 정보와 예방법, 치료 방법을 제공하는 웹 서비스 입니다.



## 📖 Description

비대면 진료와 생성형 AI를 결합한 웹 서비스로, 질병 정보와 예방법을 실시간으로 제공합니다.
JWT 로그인, 웹 크롤링, 의사 권한 구분 등으로 정보의 신뢰성과 보안성을 강화했습니다.
기존 시스템 대비 응답 속도를 개선해 사용자 대기 시간을 대폭 단축했습니다.

## ⭐ Main Feature
### 질병 정보 실시간 응답
- 생성형 AI를 통해 사용자의 질병 관련 질문에 즉각적인 답변 제공

### 전문가 피드백 기능
- 의사 계정으로 로그인 시 질병 정보에 전문 의견 추가 가능

### 질병 정보 수집
- BeautifulSoup을 활용해 다양한 질병 정보(설명, 예방법 등) 크롤링 및 저장

### JWT 기반 인증 및 권한 분리
- 사용자 및 의료진 계정에 대한 로그인, 인증 및 역할 구분

### 사용자 친화적 UI
- 간결한 인터페이스로 누구나 쉽게 질문하고 정보를 얻을 수 있는 환경 제공

## 🔧 Stack
- **Language**: Java 17
- **Framework**: Spring Boot 3.3.7
- **Libraries**:
    - **Lombok**: 1.18.36
    - **MapStruct**: 1.5.2.Final
    - **Spring Security**: 3.3.7
    - **JJWT**: 0.11.5 (JWT 토큰 처리)
    - **Spring Cloud AWS**: 2.2.6.RELEASE
    - **QueryDSL**: 5.0.0 (jakarta)
    - **Springdoc OpenAPI UI**: 2.3.0
    - **Spring Boot DevTools**: 3.3.7 (개발용 도구)
    - **Spring Boot Starter Validation**: 3.3.7
    - **Spring Boot Starter OAuth2 Client**: 3.3.7 (OAuth 2.0 인증)
    - **Spring Boot Starter Mail**: 3.3.7 (이메일 전송)
    - **Dotenv Java**: 3.0.0 (환경 변수 관리)
- **Database**: MySQL
- **Deploy**:
    - 미구현


## :open_file_folder: Project Structure

```markdown
src
├─main
│  ├─java
│  │  └─com
│  │      └─medical_web_service
│  │          └─capstone
│  │              ├─config
│  │              │  ├─handler
│  │              │  └─jwt
│  │              ├─controller
│  │              ├─dto
│  │              ├─entity
│  │              ├─repository
│  │              └─service
│  └─resources
│      └─static
│          └─csv
└─test


```
### 💬 **의사소통 및 협업 도구**
- **브레인스토밍**을 통해 기능 확정
- **Discord**: 기능 구현 상태 확인 및 이슈 공유
- **Notion**: 문서화 및 일정 관리

## 👨‍👩‍👧‍👦 Developer
*  **백승일**
