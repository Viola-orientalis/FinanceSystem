package com.exam.mapper;

import com.exam.dto.TodoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoMapper {
  List<TodoDTO> findAll(String userid);
  TodoDTO findById(int id);

  int save(TodoDTO dto);
  int updateById(TodoDTO dto);
  int deleteById(int id);
}
