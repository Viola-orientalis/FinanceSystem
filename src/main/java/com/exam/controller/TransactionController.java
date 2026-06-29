package com.exam.controller;

import com.exam.dto.AccountDTO;
import com.exam.dto.TransactionDTO;
import com.exam.dto.TransactionRequestDTO;
import com.exam.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // 입금 화면
    @GetMapping("/deposit")
    public String depositForm(Model model) {
        model.addAttribute("transactionRequestDTO", new TransactionRequestDTO());
        return "deposit";
    }

    // 입금
    @PostMapping("/deposit")
    public String deposit(Authentication authentication,
                          @Valid @ModelAttribute TransactionRequestDTO transactionRequestDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          Model model) {

        if (bindingResult.hasErrors()) {
            return "deposit";
        }

        try {
            String userid = authentication.getName();

            AccountDTO account =
                    transactionService.deposit(userid, transactionRequestDTO.getAmount());

            redirectAttributes.addFlashAttribute("message", "입금이 완료되었습니다.");

            return "redirect:/transactions";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "deposit";
        }
    }

    // 출금 화면
    @GetMapping("/withdraw")
    public String withdrawForm(Model model) {
        model.addAttribute("transactionRequestDTO", new TransactionRequestDTO());
        return "withdraw";
    }

    // 출금
    @PostMapping("/withdraw")
    public String withdraw(Authentication authentication,
                           @Valid @ModelAttribute TransactionRequestDTO transactionRequestDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        if (bindingResult.hasErrors()) {
            return "withdraw";
        }

        try {
            String userid = authentication.getName();

            AccountDTO account =
                    transactionService.withdraw(userid, transactionRequestDTO.getAmount());

            redirectAttributes.addFlashAttribute("message", "출금이 완료되었습니다.");

            return "redirect:/transactions";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "withdraw";
        }
    }

    // 거래 내역 조회
    @GetMapping("/transactions")
    public String transactions(Authentication authentication, Model model) {

        String userid = authentication.getName();

        List<TransactionDTO> transaction =
                transactionService.findMyTransactions(userid);

        model.addAttribute("transactions", transaction);

        return "transactions";
    }
}