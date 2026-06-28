package com.exam.controller;

import com.exam.dto.AccountDTO;
import com.exam.service.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 계좌 조회 화면
    @GetMapping("/account")
    public String account(Authentication authentication, Model model) {

        String userid = authentication.getName();

        AccountDTO account = accountService.findMyAccount(userid);

        model.addAttribute("account", account);

        return "account";
    }
}