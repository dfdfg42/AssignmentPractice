# 과제형 구현 연습 모노레포 통합 설계 (AssignmentPractice)

작성일: 2026-09-16
대상 계정: github.com/dfdfg42
선행 작업: woowacourse-precourse, spring-study 통합 (같은 날, 같은 방식)

## 목표

과제형 실제 구현 연습 프로젝트를 `dfdfg42/AssignmentPractice` 모노레포로 모은다. GitHub 리포 2개는 히스토리째 이관하고, 로컬에만 있던 실습 폴더 1개는 출처(원본 스냅샷)와 본인 변경을 커밋으로 분리해 올린다. 별도로 `dfdfg42/RedisFailureLabs` 리포를 만들어 실습 자료 사본을 올린다.

## 범위

### AssignmentPractice (public) 에 들어가는 것

| 폴더 (camelCase) | 출처 | 브랜치 | 커밋 | 방식 |
|---|---|---|---|---|
| couponService | GitHub dfdfg42/CouponService (private) | main | 6 | 경로 재작성 후 머지 |
| reactTimer | GitHub dfdfg42/plang_project (public) | master | 3 | 경로 재작성 후 머지 |
| backendFailureLabs | 로컬 `C:\Users\dfdfg\IdeaProjects\backend_failure_labs-main` | - | 신규 2개 | 원본 스냅샷 커밋 + 본인 변경 커밋 |

- plang_project 는 로컬에 커밋 안 된 변경(README worker 설명 추가, docs/architecture.md 삭제)이 있었고, 사용자 승인으로 2026-09-16 커밋·push 완료 (3커밋).
- backend_failure_labs-main 은 charsyam/backend_failure_labs 를 zip으로 받은 뒤 작업한 폴더. 라이선스 없음. 줄바꿈(CRLF) 차이를 빼면 본인 변경은 다음 12개:
  - 추가: lab1/scripts/reproduce-lost-update.ps1, lab2/scripts/create-1000-posts.ps1, lab2/.../post/PostJdbcRepository.kt, lab4/scripts/seed.sh, lab4/src/ 전체, lab5/scripts/generate-load.sh, lab5/scripts/seed.sh, lab5/scripts/watch-search.sh
  - 수정: lab4/item-service/.../ItemService.kt, lab5/build.gradle.kts, lab5/.../order/OrderRepository.kt, lab5/.../payment/PaymentService.kt

### RedisFailureLabs (public, 별도 리포)

- 로컬 `C:\Users\dfdfg\IdeaProjects\redis_failure_labs-main` 은 charsyam/redis_failure_labs 와 내용이 동일(본인 변경 0). 사용자 결정으로 별도 리포에 파일만 올린다.
- 커밋 2개: (1) 원본 스냅샷 (2) README 맨 위에 출처 한 줄 추가. 리포 설명에도 원본 링크.

### 이름 규칙

- 새 리포 이름은 파스칼(AssignmentPractice, RedisFailureLabs). 사용자가 직접 바꾼 리포들(DiceOrbit 등)과 통일. 오늘 만든 woowacourse-precourse, spring-study 는 그대로 둔다(사용자 결정).
- 폴더 이름은 camelCase.

## 결과물 구조

```
AssignmentPractice/           (public)
├── README.md                 표: 폴더, 주제, 커밋, 출처
├── .gitignore                루트용 (.idea/, *.iml, .vscode/, .DS_Store, .claude/)
├── docs/2026-09-16-assignment-practice-design.md
├── couponService/            쿠폰시스템 설계 실습 (Java/Spring)
├── reactTimer/               React + Vite 타이머 (IDLE/RUNNING/PAUSED, worker tick)
└── backendFailureLabs/       DB/백엔드 장애 실습 lab1~lab6 (Kotlin/Spring, MySQL)

RedisFailureLabs/             (public)  charsyam/redis_failure_labs 사본 + 출처 표기
```

- 로컬 클론: `C:\Users\dfdfg\source\AssignmentPractice`, `C:\Users\dfdfg\source\RedisFailureLabs`
- 각 폴더는 자체 빌드 설정 유지. 루트 빌드 없음.

## 이관 방식

### GitHub 리포 2개
spring-study 와 동일. `import.sh <폴더> <브랜치> <원본URL> <모노레포경로>` 로 경로 재작성 후 `--allow-unrelated-histories` 머지. 트리 해시 검증.

### backendFailureLabs (로컬 폴더)
1. charsyam/backend_failure_labs 를 얕게 클론해 `git archive` 로 `backendFailureLabs/` 에 풀고 커밋: `chore: backendFailureLabs 원본 스냅샷 (charsyam/backend_failure_labs@<sha>)`
2. 로컬 폴더를 `.gradle/ .kotlin/ build/ out/ .idea/ .claude/` 제외하고 그 위에 덮어쓰기. 텍스트 파일 CRLF→LF 정규화(원본이 LF라 줄바꿈만 다른 파일이 변경으로 잡히지 않게). 커밋: `feat: backendFailureLabs 실습 구현 (lab1~lab5 본인 변경)`
3. 검증: 2번 커밋의 변경 파일 목록이 위 12개 범위 안이어야 함. 모노레포 폴더와 로컬 폴더를 `diff -rq --strip-trailing-cr` 로 비교해 차이 0.

### RedisFailureLabs
charsyam/redis_failure_labs 얕은 클론 → `git archive` 로 새 리포에 풀고 커밋 → README 첫 줄에 `> 원본: https://github.com/charsyam/redis_failure_labs (실습용 사본)` 추가 커밋 → push. 로컬 폴더와 `diff -rq --strip-trailing-cr` 차이 0 확인.

## 검증 (삭제 전 필수)

1. couponService, reactTimer: 원본 최신 SHA = 이관 시점 SHA, 트리 해시 일치, 폴더 커밋 수 ≥ 원본.
2. backendFailureLabs: 로컬 폴더와 diff 0 (줄바꿈 무시), 본인 변경 커밋의 파일 목록이 12개 범위.
3. RedisFailureLabs: 로컬 폴더와 diff 0 (README 출처 줄 제외).
4. 두 리포 모두 원격 = 로컬 HEAD, 워킹트리 클린.

## 삭제

- GitHub: CouponService, plang_project (fork 아님, 90일 내 복구 가능). 사용자 명시 확인 후.
- 로컬 폴더(IdeaProjects/couponService, source/plang_project, IdeaProjects/backend_failure_labs-main, IdeaProjects/redis_failure_labs-main)는 삭제하지 않고 사용자에게 맡긴다. 원하면 같은 확인에서 지정.

## 실패 대응

이전 설계와 동일. 재작성은 임시 클론에서만, 머지 실패 시 reset, 검증 실패 시 삭제 안 함. 로컬 폴더는 읽기만 하고 절대 수정하지 않는다.
