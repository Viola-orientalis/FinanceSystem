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
  // 도커로 실행 시 host.docker.internal을 통해 호스트의 8080 포트로 접근
  const res = http.get('http://host.docker.internal:8080/');
  
  check(res, {
    'is status 200': (r) => r.status === 200,
  });
  
  sleep(1);
}
