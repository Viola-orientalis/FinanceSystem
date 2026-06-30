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
  // 환경변수로 URL을 받거나, K8s Ingress 도메인(finance.local)을 기본값으로 사용
  const baseUrl = 'http://172.16.8.12:32311';
  const params = {
    headers: {
      'Host': 'finance.local',
    },
  };
  const res = http.get(`${baseUrl}/`,params);

  check(res, {
    'is status 200': (r) => r.status === 200,
  });

  sleep(1);
}