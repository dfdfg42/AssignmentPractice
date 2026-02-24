import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
// 메인 컴포넌트
import App from './App.tsx'

// 루트에 앱 렌더링
createRoot(document.getElementById('root')!).render(
  // 개발용 검사 모드
  <StrictMode>
    <App />
  </StrictMode>,
)
