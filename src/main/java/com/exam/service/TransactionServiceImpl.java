package com.exam.service;

import com.exam.dto.AccountDTO;
import com.exam.dto.TransactionDTO;
import com.exam.mapper.AccountMapper;
import com.exam.mapper.TransactionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(AccountMapper accountMapper,
                                  TransactionMapper transactionMapper) {
        this.accountMapper = accountMapper;
        this.transactionMapper = transactionMapper;
    }

    // 입금 처리
    @Override
    @Transactional
    public AccountDTO deposit(String userid, Long amount) {

        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("입금 금액은 1원 이상이어야 합니다.");
        }

        AccountDTO account = accountMapper.findByUseridForUpdate(userid);

        if (account == null) {
            throw new IllegalArgumentException("계좌가 존재하지 않습니다.");
        }

        Long beforeBalance = account.getBalance();
        Long afterBalance = beforeBalance + amount;

        accountMapper.updateBalance(account.getId(), afterBalance);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountId(account.getId());
        transactionDTO.setType("DEPOSIT");
        transactionDTO.setAmount(amount);
        transactionDTO.setBalanceBefore(beforeBalance);
        transactionDTO.setBalanceAfter(afterBalance);
        transactionDTO.setCreatedAt(LocalDateTime.now());

        transactionMapper.save(transactionDTO);

        account.setBalance(afterBalance);

        return account;
    }

    // 출금 처리
    @Override
    @Transactional
    public AccountDTO withdraw(String userid, Long amount) {

        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("출금 금액은 1원 이상이어야 합니다.");
        }

        // 1회 최대 한도 제한 체크
        if (amount > 1_000_000) {
            throw new IllegalArgumentException("요청하신 금액이 한도를 초과했습니다 (1회 최대 한도: 1,000,000원)");
        }

        AccountDTO account = accountMapper.findByUseridForUpdate(userid);

        if (account == null) {
            throw new IllegalArgumentException("계좌가 존재하지 않습니다.");
        }

        Long beforeBalance = account.getBalance();

        if (beforeBalance < amount) {
            throw new IllegalArgumentException("잔액을 초과하여 출금할 수 없습니다.");
        }

        Long afterBalance = beforeBalance - amount;

        accountMapper.updateBalance(account.getId(), afterBalance);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountId(account.getId());
        transactionDTO.setType("WITHDRAW");
        transactionDTO.setAmount(amount);
        transactionDTO.setBalanceBefore(beforeBalance);
        transactionDTO.setBalanceAfter(afterBalance);
        transactionDTO.setCreatedAt(LocalDateTime.now());

        transactionMapper.save(transactionDTO);

        account.setBalance(afterBalance);

        return account;
    }

    // 거래 내역 조회
    @Override
    public List<TransactionDTO> findMyTransactions(String userid) {

        AccountDTO account = accountMapper.findByUserid(userid);

        if (account == null) {
            throw new IllegalArgumentException("계좌가 존재하지 않습니다.");
        }

        return transactionMapper.findByAccountId(account.getId());
    }
}