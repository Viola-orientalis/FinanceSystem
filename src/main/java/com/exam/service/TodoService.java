package com.exam.service;

import com.exam.dto.TodoDTO;

import java.util.List;

public interface TodoService {
    List<TodoDTO> findAll(String userid);
    TodoDTO findById(int id);

    int save(TodoDTO dto);
    int updateById(TodoDTO dto);
    int deleteById(int id);

}
