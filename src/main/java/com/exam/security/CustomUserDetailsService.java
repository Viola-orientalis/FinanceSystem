package com.exam.security;

import com.exam.dto.MemberDTO;
import com.exam.service.MemberService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    MemberService memberService;
    public CustomUserDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    // 사용자가 loginForm에서 로그인 버튼을 클릭했을 때 자동호출
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 실제로 사용되는 식별자는 userid임.
        // DB에서 해당되는 username(정확히는 userid)가 존재하는지 체크
        // 비번는 내부적으로 체크함. ( SpringSecurity 6버전부터 지원)
        MemberDTO dto = memberService.findById(username);
        if(dto == null){
            throw new UsernameNotFoundException(username+" 에 해당하는 사용자는 없습니다.");
        }
        // 세션(SecurityContextHolder)에 저장할 객체 생성
//        return new CustomUserDetails(dto, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        return new CustomUserDetails(dto, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
