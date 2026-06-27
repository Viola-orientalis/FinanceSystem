import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 50 },  // 30초 동안 50명으로 증가
    { duration: '30s', target: 100 }, // 다음 30초 동안 100명으로 증가
    { duration: '10s', target: 0 },   // 10초 동안 0명으로 감소 (쿨다운)
  ],
};

export default function () {
  // 실제 서버 IP/도메인으로 변경 필요
  const res = http.get('http://localhost:8080/home');
  
  check(res, {
    'is status 200': (r) => r.status === 200,
  });
  
  sleep(1);
}
