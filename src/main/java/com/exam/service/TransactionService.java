package com.exam.service;

import com.exam.dto.AccountDTO;
import com.exam.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {

    // 입금
    public AccountDTO deposit(String userid, Long amount);

    // 출금
    public AccountDTO withdraw(String userid, Long amount);

    // 사용자 거래 내역 조회
    public List<TransactionDTO> findMyTransactions(String userid);
}