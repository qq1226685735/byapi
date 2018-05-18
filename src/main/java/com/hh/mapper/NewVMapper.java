package com.hh.mapper;

import com.hh.entity.New;
import com.hh.entity.NewV;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewVMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewV record);

    int insertSelective(NewV record);
    List<NewV> selectNewvByNumber(int location, int number);
    NewV selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewV record);

    int updateByPrimaryKey(NewV record);
}