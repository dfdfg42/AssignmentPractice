import { useState, useEffect, useRef, useCallback } from 'react';

// 타이머 상태
export type TimerStatus = 'IDLE' | 'RUNNING' | 'PAUSED';

interface UseTimerReturn {
  timeInSeconds: number; // 표시 시간(초)
  status: TimerStatus; // 현재 상태
  targetTime: number | null; // 목표 시간
  start: () => void; // 시작
  pause: () => void; // 일시정지
  reset: () => void; // 초기화
  setTargetTime: (seconds: number | null) => void; // 목표 설정
  isTargetReached: boolean; // 목표 도달 여부
}

export function useTimer(): UseTimerReturn {
  // 화면 상태
  const [status, setStatus] = useState<TimerStatus>('IDLE');
  const [timeInSeconds, setTimeInSeconds] = useState(0);
  const [targetTime, setTargetTimeState] = useState<number | null>(null);
  const [isTargetReached, setIsTargetReached] = useState(false);

  // 내부 참조값
  const startTimeRef = useRef<number | null>(null);

  // 누적 경과 시간(ms)
  const accumulatedTimeRef = useRef<number>(0);

  // 워커 참조
  const workerRef = useRef<Worker | null>(null);

  // 최신 목표 시간 참조
  const targetTimeRef = useRef<number | null>(null);

  // 목표 시간 동기화
  useEffect(() => {
    targetTimeRef.current = targetTime;
  }, [targetTime]);

  // 워커 생성/해제
  useEffect(() => {
    // 워커 생성
    workerRef.current = new Worker('/timerWorker.js');

    // TICK 수신 처리
    workerRef.current.onmessage = (e: MessageEvent) => {

      // TICK일 때만 계산
      if (e.data.type === 'TICK') {
        if (startTimeRef.current === null) return;

        // 경과 시간(ms) 계산
        const now = Date.now();
        const elapsedTimeMs = (now - startTimeRef.current) + accumulatedTimeRef.current;

        // 초 단위 변환
        const currentSeconds = Math.floor(elapsedTimeMs / 1000);

        // 오차 로그
        const driftMs = elapsedTimeMs - (currentSeconds * 1000);
        console.log(
          `[Timer] 흐른 시간: ${elapsedTimeMs}ms | 화면에 표시할 초: ${currentSeconds}s | 오차: ${driftMs.toFixed(1)}ms`
        );

        // 초가 바뀔 때만 갱신
        setTimeInSeconds((prev: number) => {
          if (prev !== currentSeconds) {
            // 목표 도달 확인
            if (targetTimeRef.current !== null && currentSeconds >= targetTimeRef.current) {
              setIsTargetReached(true);
            }
            return currentSeconds;
          }
          return prev; // 변경 없음
        });
      }
    };

    // 언마운트 시 워커 종료
    return () => {
      if (workerRef.current) {
        workerRef.current.terminate();
      }
    };
  }, []);

  // 동작 함수

  const start = useCallback(() => {
    if (status === 'RUNNING') return; // 실행 중이면 무시

    // 시작 시각 기록
    startTimeRef.current = Date.now();
    setStatus('RUNNING');

    // 워커 시작 요청(100ms)
    workerRef.current?.postMessage({ type: 'START', payload: { interval: 100 } });

    // 새 시작 시 도달 상태 초기화
    if (status === 'IDLE' && targetTime !== null && timeInSeconds < targetTime) {
      setIsTargetReached(false);
    }
  }, [status, targetTime, timeInSeconds]);

  const pause = useCallback(() => {
    if (status !== 'RUNNING') return; // 실행 중이 아니면 무시

    // 워커 정지 요청
    workerRef.current?.postMessage({ type: 'STOP' });

    // 현재까지 경과 시간 누적
    if (startTimeRef.current !== null) {
      const now = Date.now();
      accumulatedTimeRef.current += (now - startTimeRef.current);
    }

    setStatus('PAUSED');
  }, [status]);

  const reset = useCallback(() => {
    workerRef.current?.postMessage({ type: 'STOP' }); // 워커 정지
    setStatus('IDLE'); // 상태 초기화
    setTimeInSeconds(0); // 시간 초기화
    setIsTargetReached(false); // 도달 상태 초기화

    // 내부 참조 초기화
    startTimeRef.current = null;
    accumulatedTimeRef.current = 0;
  }, []);

  const setTargetTime = useCallback((seconds: number | null) => {
    setTargetTimeState(seconds);
    setIsTargetReached(false);
    // 이미 초과한 목표는 즉시 도달 처리
    if (seconds !== null && timeInSeconds >= seconds) {
      setIsTargetReached(true);
    }
  }, [timeInSeconds]);

  // 외부 반환값
  return {
    timeInSeconds,
    status,
    targetTime,
    start,
    pause,
    reset,
    setTargetTime,
    isTargetReached
  };
}
