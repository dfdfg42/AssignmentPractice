# 문제 4. Key-Value 저장소

> 지문은 구현 코드에서 역추적해 다시 쓴 것이다. 원본과 다를 수 있다.

## 지문

문자열 키-값을 저장하는 인메모리 저장소를 만든다. 세 가지 명령을 지원한다.

| 명령 | 동작 |
|---|---|
| `GET key` | 값을 돌려준다. 키가 없거나 만료됐으면 없음(null) |
| `SET key value` | 값을 저장한다. **키가 이미 있으면 값만 바꾸고 기존 만료 시각은 유지한다** |
| `EXPIRE key seconds` | `seconds` 초 뒤에 키가 삭제되도록 예약한다. 키가 없거나 이미 만료됐으면 실패 |

**요구사항**:

- 여러 클라이언트가 동시에 접근해도 안전해야 한다.
- 만료된 키는 실제로 메모리에서 제거돼야 한다 (조회 시점에만 걸러내는 것으로는 부족).
- 선택: 프로그램을 재시작해도 데이터가 남는 영속성, 네트워크로 접근하는 API.

## 실행

라이브러리 단독 확인:

```bash
# KVStore.main 실행: SET → EXPIRE → 다시 SET 해도 만료 유지 → 시간 지나면 null 시나리오 출력
```

HTTP API (선택 요건):

```bash
./gradlew bootRun          # 8080 포트
curl -X POST localhost:8080/kv/name -H 'Content-Type: application/json' -d '{"value":"alice"}'
curl localhost:8080/kv/name
curl -X POST localhost:8080/kv/name/expire -H 'Content-Type: application/json' -d '{"seconds":60}'
```

| 메서드 | 경로 | 설명 |
|---|---|---|
| GET | `/kv/{key}` | `{"key","value"}`. 없거나 만료면 404 |
| POST | `/kv/{key}` | 본문 `{"value": "..."}` |
| POST | `/kv/{key}/expire` | 본문 `{"seconds": 60}`. 키 없으면 404 |

## 풀이 요약

`ConcurrentHashMap<String, Entry>`에 값과 만료 시각(`expireAt`, null이면 영구)을 함께 둔다. SET은 `compute()`로 원자적으로 값만 교체해 만료 시각을 보존한다. 만료는 두 겹으로 처리한다. GET 시점에 확인해 지우는 lazy 삭제와, 1초 주기 스케줄러가 전체를 훑는 eager 삭제.

| 파일 | 역할 |
|---|---|
| `KVStore.java` | 저장소 본체 (GET/SET/EXPIRE, 만료 정리, 확인용 main) |
| `KVController.java` | HTTP API |
| `KvStoreApplication.java` | Spring Boot 진입점 |
| `Entry.java` | 값+만료 시각 객체 (초기 설계 잔재. `KVStore`는 내부 클래스를 사용) |
