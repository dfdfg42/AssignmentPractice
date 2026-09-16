import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// 빌드 도구 설정
export default defineConfig({
  plugins: [react()],
})
