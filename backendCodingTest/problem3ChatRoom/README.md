# 문제 3. 채팅방 메시지 서버

> 지문은 구현 코드에서 역추적해 다시 쓴 것이다. 원본과 다를 수 있다.

## 지문

여러 클라이언트가 방 단위로 메시지를 주고받는 HTTP 서버를 만든다.

**API**:

| 메서드 | 경로 | 설명 |
|---|---|---|
| POST | `/rooms/{roomId}/messages` | 본문 `{"sender": "...", "content": "..."}`. 저장된 메시지(id, sender, content, timestamp)를 돌려준다 |
| GET | `/rooms/{roomId}/messages?after={id}` | id가 `after`보다 큰 메시지만 돌려준다. `after=0`이면 방의 모든 메시지 |

**요구사항**:

- 메시지 id는 단조 증가해야 하며, 클라이언트는 마지막으로 읽은 id를 `after`로 넘겨 그 이후 메시지만 받아간다.
- 메시지는 24시간만 보관한다. 지난 메시지는 조회에서 빠지고 저장소에서도 정리돼야 한다.
- 여러 클라이언트가 동시에 보내고 읽어도 메시지가 유실되거나 순서가 꼬이면 안 된다.

## 실행

```bash
./gradlew bootRun          # 8080 포트
```

서버를 띄운 뒤 `TestClient.main`을 따로 실행하면 전송 → 전체 조회 → 추가 전송 → 이후분 조회 흐름을 출력한다.

```bash
curl -X POST localhost:8080/rooms/room1/messages -H 'Content-Type: application/json' -d '{"sender":"alice","content":"hi"}'
curl 'localhost:8080/rooms/room1/messages?after=0'
```

## 풀이 요약

`ConcurrentHashMap<roomId, List<Message>>` + `AtomicLong` id 카운터. 쓰기는 `synchronized`로 직렬화하고, 읽기는 스트림으로 `id > after`와 24시간 이내 조건을 걸러 반환한다. `@Scheduled` 1분 주기로 만료 메시지를 삭제한다.

| 파일 | 역할 |
|---|---|
| `ChatRoomApplication.java` | Spring Boot 진입점, `@EnableScheduling` |
| `MessageController.java` | POST/GET 엔드포인트 |
| `RoomRepository.java` | 방별 메시지 저장·조회·만료 정리 |
| `MessageCleanupScheduler.java` | 1분마다 24시간 지난 메시지 제거 |
| `Message.java` | 메시지 값 객체 |
| `TestClient.java` | 동작 확인용 HTTP 클라이언트 |
