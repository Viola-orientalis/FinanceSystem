package com.exam.controller;

import com.exam.dto.MemberDTO;
import com.exam.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public MemberController(MemberService memberService,
                            PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 화면
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "memberForm";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute MemberDTO memberDTO,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "memberForm";
        }

        try {
            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());
            memberDTO.setPassword(encodedPassword);

            // 기본 권한
            if (memberDTO.getRole() == null || memberDTO.getRole().isBlank()) {
                memberDTO.setRole("USER");
            }

            // 회원가입
            memberService.signup(memberDTO);

            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "회원가입 중 오류가 발생했습니다.");
            return "memberForm";
        }
    }
}