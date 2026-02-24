## 1) 상태 모델 설계

타이머 상태는 `IDLE | RUNNING | PAUSED` 세 가지로 분리했습니다.

- `IDLE`: 초기 상태 또는 Reset 직후 상태
- `RUNNING`: 시간이 증가 중인 상태
- `PAUSED`: 증가를 멈추고 현재 시간을 유지한 상태


`isRunning: boolean` 하나만 사용하면 다음을 분리하기 어렵습니다.

- “아직 시작 안 함(Idle)”
- “중간에 멈춤(Paused)”

이 둘이 분리되어야 버튼 UX를 정확히 제어할 수 있습니다.

- 실행 중일 때 `Start` 비활성화
- 실행 중이 아닐 때 `Pause` 비활성화
- Pause → Start에서 정확히 이어서 진행


---

## 2) 시간 계산 방식

### 문제 인식

일반적인 타이머 애플리케이션에서는 `setInterval`을 호출하여 1초마다 초를 증가시키는 방식을 사용합니다. 
하지만 자바스크립트는 싱글 스레드와 이벤트 루프 기반으로 동작하기 때문에 메인 스레드에 무거운 작업이 몰리면 실행 주기를 완벽하게 보장하지 못합니다. 
또한 백그라운드 탭으로 전환하면 브라우저가 타이머 API의 호출 주기를 강제로 늦추거나, 다시 탭으로 전환한 이후에 알람이 울릴 수 있습니다
때문에 worker를 사용해서 메인 스레드에 영향을 주지 않고 타이머를 실행합니다

### 해결 방식

`+1` 카운팅 방식이 아니라,
- 시작 시각 (startTime)
- 누적 실행 시간 (accumulatedTime)
- 현재 시각 (Date.now())

을 이용해 경과 시간(절대 시간 차이)을 계산합니다.
`elapsedMs = (Date.now() - startTime) + accumulatedTime`

startTime 은 start 를 누르면 startTime현재시각으로 지정되고 상태가 RUNNING 으로 변화합니다
pause 를 누르면 상태가 PAUSED 로 바뀌고 현재시각(now) 에서 startTime 을 뺀 값을 accumulatedTime(타이머를 누르고 흐른 시간) 에 더합니다
다시 start 를 누르면 startTime 은 현재시각으로 지정되고 상태가 RUNNING 으로 변화합니다
이떄 accumulatedTime 에 저장된 값이 더해져서 멈췄던 시간만큼 다시 계산됩니다

reset 을 누르면 상태가 IDLE 로 바뀌고 startTime 과 accumulatedTime 을 0으로 초기화합니다

이를 통해 앱이 멈췄다 풀려도 실제 시간과 오차 없이 동기화됩니다.

시간은 elapsedMs 를 1000으로 나눈 값으로 표시합니다.

---


---

