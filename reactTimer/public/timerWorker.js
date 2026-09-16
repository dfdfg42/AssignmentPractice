let intervalId = null;

// 메인 스레드 메시지 처리
self.onmessage = function (e) {
  const { type, payload } = e.data;

  if (type === 'START') {
    // 기존 인터벌 정리
    if (intervalId) clearInterval(intervalId);

    // 요청 간격으로 TICK 전송
    intervalId = setInterval(() => {
      self.postMessage({ type: 'TICK' });
    }, payload?.interval || 100);

  } else if (type === 'STOP') {
    if (intervalId) {
      clearInterval(intervalId);
      intervalId = null;
    }
  }
};
