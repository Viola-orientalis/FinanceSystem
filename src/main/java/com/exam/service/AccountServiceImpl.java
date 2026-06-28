package com.exam.service;

import com.exam.dto.AccountDTO;
import com.exam.mapper.AccountMapper;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    // 사용자 계좌 조회
    @Override
    public AccountDTO findMyAccount(String userid) {
        return accountMapper.findByUserid(userid);
    }
}