package com.exam.controller;

import com.exam.dto.TodoDTO;
import com.exam.mapper.TodoMapper;
import com.exam.security.CustomUserDetails;
import com.exam.service.TodoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller

public class TodoController {
   TodoService todoService;
   public TodoController(TodoService todoService) {
     this.todoService = todoService;
   }
    @GetMapping("/todo-list")
    public String  todoList (@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
      // 로그인 계정 얻기
//        String userid = (String)model.getAttribute("my_login");
        String userid = userDetails.getUsername();
        List<TodoDTO> todoList = todoService.findAll(userid);;
        //모델에 저장
        model.addAttribute("todoListFindAll",todoList);
        return "todoList"; // todoList.html
    }
    //화면 필요
    @GetMapping("/todo-add")
    public String  todoAddForm() {
        return "todoAddForm";
    }
    @PostMapping("/todo-add")
    public String  todoAdd(@AuthenticationPrincipal CustomUserDetails userDetails,TodoDTO dto, Model model) {
        //로그인된 userid 얻기
//        String userid = (String)model.getAttribute("my_login");
        String userid = userDetails.getUsername();
        dto.setUserid(userid);
        int n = todoService.save(dto);
        System.out.println(dto);
        return "redirect:todo-list";
    }
}
