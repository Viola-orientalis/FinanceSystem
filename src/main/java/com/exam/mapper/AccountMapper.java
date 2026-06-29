package com.exam.mapper;

import com.exam.dto.AccountDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    // 회원가입 시 계좌 생성
    public int createAccount(@Param("userid") String userid,
                             @Param("accountNumber") String accountNumber);

    // 계좌 조회
    public AccountDTO findByUserid(String userid);

    // 입금 출금시 계좌 조회
    public AccountDTO findByUseridForUpdate(String userid);

    // 잔고 수정
    public int updateBalance(@Param("id") Long id,
                             @Param("balance") Long balance);
}