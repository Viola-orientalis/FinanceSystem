package com.exam.service;

import com.exam.dto.MemberDTO;

public interface MemberService {
    String signup(MemberDTO dto);
    MemberDTO findById(String userid);
}
