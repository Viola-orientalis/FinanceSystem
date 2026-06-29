package com.exam.service;

import com.exam.dto.AccountDTO;

public interface AccountService {

    // 사용자 계좌 조회
    public AccountDTO findMyAccount(String userid);
}