package com.exam.mapper;

import com.exam.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MemberMapper {
    int signup(MemberDTO dto);
    //MemberDTO authenticate(Map<String,String> map);
    MemberDTO findById(String userid);

}
