# AssignmentPractice

과제형 구현 연습 모음. 흩어져 있던 리포와 로컬 폴더를 하나로 모은 모노레포다.

- 폴더별로 독립 프로젝트다. IDE에서 폴더 단위로 연다.
- GitHub에 있던 프로젝트는 커밋 히스토리를 그대로 가져왔다. `git log -- <폴더>` 또는 GitHub 폴더 History로 볼 수 있다.
- 통합 과정은 [docs/2026-09-16-assignment-practice-design.md](docs/2026-09-16-assignment-practice-design.md) 참고.

## 목록

| 폴더 | 주제 | 스택 | 커밋 | 출처 |
|---|---|---|---|---|
| [couponService](couponService) | 쿠폰 시스템 설계 실습 | Java, Spring | 6 | dfdfg42/CouponService |
| [reactTimer](reactTimer) | 타이머 앱. IDLE/RUNNING/PAUSED 상태 모델, Worker 기반 tick | TypeScript, React, Vite | 3 | dfdfg42/plang_project |
| [backendFailureLabs](backendFailureLabs) | DB/백엔드 장애 실습 lab1~lab6 (lost update, batch insert, 페이지네이션, 분산 트랜잭션 등) | Kotlin, Spring Boot, MySQL, Docker | 2 | [charsyam/backend_failure_labs](https://github.com/charsyam/backend_failure_labs) 원본 스냅샷 + 본인 구현 |
| [redisFailureLabs](redisFailureLabs) | Redis 장애 실습 step_1~step_4 | Python, Redis, Docker | 2 | [charsyam/redis_failure_labs](https://github.com/charsyam/redis_failure_labs) 원본 스냅샷 (실습용 사본) |
| [backendCodingTest](backendCodingTest) | 백엔드 코딩 테스트 4문제, 문제별 독립 프로젝트: 카드 조합 비교, 병렬 크롤러, 채팅방 메시지 서버, 만료 지원 KV 저장소. 문제 지문은 코드에서 역추적해 재구성 | Java 17, Spring Boot | 2 | 로컬 과제 폴더 (히스토리 없음) |
| [pixelCanvas](pixelCanvas) | 16×16 공용 픽셀 캔버스 서버 (쿨타임, 랭킹). **미완성, 컴파일 안 됨** | Java 17, Spring Boot | 1 | 로컬 과제 폴더 (히스토리 없음) |

backendFailureLabs 는 첫 커밋이 charsyam 원본 스냅샷이고, 두 번째 커밋이 본인 구현(lab1~lab5 스크립트·서비스 추가/수정)이다. redisFailureLabs 는 원본 스냅샷 그대로이며 README 첫 줄에 출처를 적었다. 두 원본 리포 모두 라이선스가 명시되어 있지 않다.
