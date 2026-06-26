package com.exam.service;

import com.exam.dto.TodoDTO;
import com.exam.mapper.TodoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{

    TodoMapper todoMapper;

    public TodoServiceImpl(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    @Override
    public List<TodoDTO> findAll(String userid) {
        return todoMapper.findAll(userid);
    }

    @Override
    public TodoDTO findById(int id) {
        return todoMapper.findById(id);
    }

    @Override
    @Transactional
    public int save(TodoDTO dto) {
        return todoMapper.save(dto);
    }

    @Override
    @Transactional
    public int updateById(TodoDTO dto) {
        return todoMapper.updateById(dto);
    }

    @Override
    @Transactional
    public int deleteById(int id) {
        return todoMapper.deleteById(id);
    }
}
