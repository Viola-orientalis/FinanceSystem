package com.exam.mapper;

import com.exam.dto.TransactionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransactionMapper {

    // 거래 내역 저장
    public int save(TransactionDTO transactionDTO);

    // 거래 내역 조회
    public List<TransactionDTO> findByAccountId(Long accountId);
}