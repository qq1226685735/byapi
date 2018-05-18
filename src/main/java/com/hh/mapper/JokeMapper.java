package com.hh.mapper;

import com.hh.entity.Joke;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JokeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Joke record);

    int insertSelective(Joke record);

    Joke selectByPrimaryKey(Integer id);
    List<Joke> selectJokeByNumber(int location, int number);
    int updateByPrimaryKeySelective(Joke record);

    int updateByPrimaryKey(Joke record);
}