import { useState } from 'react';
import { useTimer } from './hooks/useTimer';
import './App.css';

function App() {
  // 타이머 훅 값/함수
  const {
    timeInSeconds,
    status,
    start,
    pause,
    reset,
    targetTime,
    setTargetTime,
    isTargetReached
  } = useTimer();

  // 목표 입력값
  const [inputVal, setInputVal] = useState('');

  // 상태 라벨
  const statusLabelMap: Record<typeof status, string> = {
    IDLE: 'Idle',
    RUNNING: 'Running',
    PAUSED: 'Paused'
  };

  // 초를 mm:ss로 변환
  const formatTime = (totalSeconds: number) => {
    const m = Math.floor(totalSeconds / 60); // 분
    const s = totalSeconds % 60; // 초
    return `${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`;
  };

  // 목표 시간 설정
  const handleSetTarget = (e: React.FormEvent) => {
    e.preventDefault(); // 폼 기본 동작 방지
    const val = parseInt(inputVal, 10); // 숫자 변환
    if (!isNaN(val) && val > 0) { // 유효값 확인
      setTargetTime(val); // 목표 반영
      setInputVal(''); // 입력 초기화
    }
  };

  // 목표 시간 해제
  const handleClearTarget = () => {
    setTargetTime(null); // 목표 제거
  };

  // 화면 렌더링
  return (
    // 목표 도달 시 효과 클래스 적용
    <div className={`app-container ${isTargetReached ? 'target-reached' : ''}`}>

      {/* 목표 도달 알림 */}
      <div className={`alert-toast ${isTargetReached ? 'visible' : ''}`}>
        🎉 Target time of {targetTime}s reached!
      </div>

      <div className="timer-card">
        {/* 상태 배지 */}
        <div className={`status-badge ${status}`}>
          {statusLabelMap[status]}
        </div>

        {/* 현재 시간 */}
        <div className="time-display">
          {formatTime(timeInSeconds)}
        </div>

        {/* 목표 시간 입력 */}
        <div className="target-settings">
          <form className="target-input-group" onSubmit={handleSetTarget}>
            <input
              type="number"
              className="target-input"
              placeholder="Target (sec)"
              value={inputVal}  // 입력 상태 바인딩
              onChange={(e) => setInputVal(e.target.value)} // 입력값 갱신
              min="1"
            />
            <button type="submit" className="target-set-btn">Set</button>
          </form>

          {/* 목표가 있을 때만 표시 */}
          {targetTime !== null && (
            <div className="target-display">
              Target: {formatTime(targetTime)}
              <button onClick={handleClearTarget} style={{ marginLeft: 8, color: 'var(--text-muted)', textDecoration: 'underline' }}>
                Clear
              </button>
            </div>
          )}
        </div>

        {/* 제어 버튼 */}
        <div className="controls">
          {/* 실행 중이면 시작 비활성화 */}
          <button
            className="control-btn btn-start"
            onClick={start}
            disabled={status === 'RUNNING'}
          >
            Start
          </button>

          {/* 실행 중일 때만 일시정지 가능 */}
          <button
            className="control-btn btn-pause"
            onClick={pause}
            disabled={status !== 'RUNNING'}
          >
            Pause
          </button>

          {/* 초기 상태에서는 리셋 비활성화 */}
          <button
            className="control-btn btn-reset"
            onClick={reset}
            disabled={status === 'IDLE' && timeInSeconds === 0}
          >
            Reset
          </button>
        </div>
      </div>
    </div>
  );
}

export default App;
