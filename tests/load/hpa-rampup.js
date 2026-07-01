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
  // 실행 전 본인 환경의 노드 IP와 NodePort로 변경하세요
  const baseUrl = 'http://<노드IP>:<NodePort>';
  const params = {
    headers: {
      'Host': 'finance.local',
    },
  };
  const res = http.get(`${baseUrl}/`, params);
  check(res, {
    'is status 200': (r) => r.status === 200,
  });
  sleep(1);
}
