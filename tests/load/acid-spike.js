import http from 'k6/http';
import { check } from 'k6';

export const options = {
  maxRedirects: 0,
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
  const baseUrl = 'http://172.16.8.12:32311';
  const url = `${baseUrl}/signup`;

  // 중복 가입 에러 방지를 위해 VU, ITER 값과 타임스탬프를 조합하여 고유 ID 생성
  const uniqueId = `user_${__VU}_${__ITER}_${Date.now()}`;

  // x-www-form-urlencoded 형식으로 페이로드 구성 (Spring @ModelAttribute 매핑용)
  const payload = `userid=${uniqueId}&password=1234&username=TestUser`;

  const params = {
    headers: {
      'Host': 'finance.local',
      'Content-Type': 'application/x-www-form-urlencoded',
    },
  };

  const res = http.post(url, payload, params);

  // 회원가입 성공 시 로그인 페이지로 리다이렉트(302) 후 로그인 페이지(200)가 로드됨
  check(res, {
    'is status 302': (r) => r.status === 302,
  });
}

