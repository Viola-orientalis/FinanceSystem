package com.exam.service;

import com.exam.dto.MemberDTO;
import com.exam.mapper.AccountMapper;
import com.exam.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final AccountMapper accountMapper;

    public MemberServiceImpl(MemberMapper memberMapper,
                             AccountMapper accountMapper) {
        this.memberMapper = memberMapper;
        this.accountMapper = accountMapper;
    }

    @Override
    @Transactional
    public String signup(MemberDTO dto) {

        int result = memberMapper.signup(dto);

        // 계좌번호 자동 생성
        String accountNumber = generateAccountNumber();

        accountMapper.createAccount(
                dto.getUserid(),
                accountNumber
        );

        return accountNumber;
    }

    @Override
    public MemberDTO findById(String userid) {
        return memberMapper.findById(userid);
    }

    // 계좌번호 생성 양식 110-xxxxxx-xxxxxx
    private String generateAccountNumber() {
        Random random = new Random();

        int middle = 100000 + random.nextInt(900000);
        int last = 100000 + random.nextInt(900000);

        return "110-" + middle + "-" + last;
    }
}