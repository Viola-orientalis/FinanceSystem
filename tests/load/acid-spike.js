import http from 'k6/http';
import { check } from 'k6';

export const options = {
  scenarios: {
    spike_test: {
      executor: 'shared-iterations',
      vus: 100,          // 100명의 가상 유저가
      iterations: 100,   // 총 100번의 요청을 (1인당 1번꼴)
      maxDuration: '5s', // 최대 5초 안에 동시에 쏘기
    },
  },
};

export default function () {
  const url = 'http://localhost:8080/withdraw';
  // 테스트용 데모 계좌 id와 1만원 출금 데이터
  const payload = JSON.stringify({
    accountId: 1,
    amount: 10000,
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const res = http.post(url, payload, params);
  
  check(res, {
    'is status 200': (r) => r.status === 200,
  });
}
