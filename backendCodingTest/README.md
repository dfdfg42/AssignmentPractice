# backendCodingTest

백엔드 코딩 테스트 문제 4개 풀이. 문제마다 독립 프로젝트라 폴더 단위로 열고 실행한다.

> 각 문제의 지문은 원본 문서가 아니라, 구현 코드에서 요구사항을 역추적해 다시 쓴 것이다. 원본과 표현·세부 조건이 다를 수 있다.

| 문제 | 폴더 | 형태 | 실행 |
|---|---|---|---|
| 1. 카드 조합 비교 | [problem1CardHands](problem1CardHands) | 순수 Java | `./gradlew run` 후 stdin 입력 |
| 2. 고성능 크롤러 | [problem2Crawler](problem2Crawler) | 순수 Java | `./gradlew run < urls.txt` |
| 3. 채팅방 메시지 서버 | [problem3ChatRoom](problem3ChatRoom) | Spring Boot HTTP 서버 | `./gradlew bootRun` |
| 4. Key-Value 저장소 | [problem4KvStore](problem4KvStore) | 라이브러리 + Spring Boot HTTP API | `./gradlew bootRun` |

공통: Java 17, Gradle wrapper 포함. 3·4번은 8080 포트를 쓰므로 동시에 띄우려면 한쪽에 `--args='--server.port=8081'`을 준다.
