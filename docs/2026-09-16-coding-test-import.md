# 코딩 테스트 과제 폴더 이관 기록 (backendCodingTest, pixelCanvas)

작성일: 2026-09-16

## 출처

로컬 `C:\Users\dfdfg\source\devsisters\` 아래 Spring Initializr 프로젝트 3개. git 히스토리 없음.

| 원본 폴더 | 내용 | 처리 |
|---|---|---|
| demo | 코딩 테스트 문제 1~4 완성본 (설계 주석 포함) | `backendCodingTest/` 로 이관 |
| prac1 | 문제 2~4의 1차 구현 + 실행 산출물(1~10.txt, kv_store_data.json) | 사용자 결정으로 제외 |
| demo3 | 픽셀 캔버스, 미완성(컴파일 오류) | `pixelCanvas/` 로 이관, README에 미완성 명시 |

## 명칭 변경

회사·게임 IP를 연상시키는 이름을 전부 중립적인 이름으로 바꿨다. 로직은 손대지 않았다.

| 원본 | 변경 |
|---|---|
| 패키지 `com.example.cookieruntalk` | `com.example.chatroom` |
| 패키지 `com.example.demo.hippos` | `com.example.cardhands` |
| 클래스 `hippos`, `Rank` | `CardHandJudge`, `HandRank` |
| 클래스 `DemoApplication`(+Tests) | `CodingTestApplication`(+Tests), 패키지 `com.example.codingtest` |
| 패키지 `com.example.demo3`, 클래스 `Demo3Application`, `Repository` | `com.example.pixelcanvas`, `PixelCanvasApplication`, `PixelRepository` |
| 테스트 문자열 "쿠키", "크림", "크림쿠키", `"cookie"`, `"devsisters"` | `"alice"`, `"bob"`, `"alice2"`, `"alice"`, `"local"` |
| 주석 "쿠키런톡", "히포즈" | "채팅방", "카드 조합 비교" |
| `rootProject.name`, `spring.application.name` | 폴더명과 동일 |

제외한 파일: `HELP.md`(Initializr 안내), `server.log`, `server_err.log`, `.idea/`, `.gradle/`, `build/`, `demo3.zip`(소스 사본).

## 문제 지문

원본 문제 문서는 리포에 넣지 않았다. 각 폴더 README의 지문은 구현 코드와 주석에서 요구사항을 역추적해 새로 쓴 것이며, 그 사실을 README 상단에 명시했다.

## 검증

- 변경 후 `devsister|데브시스터|cookie|쿠키|hippo|히포|demo|prac1` 잔존 검색 0건.
- `backendCodingTest`: `./gradlew compileJava compileTestJava` 성공 (패키지 5개 클래스 생성).
- `pixelCanvas`: 원본과 동일한 위치(PixelCanvasApplication 17행, PixelRepository 94·100행)에서 컴파일 실패. 의도된 미완성 상태.
