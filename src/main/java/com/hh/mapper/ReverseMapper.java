package com.hh.mapper;

import com.hh.entity.Reverse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReverseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Reverse record);

    int insertSelective(Reverse record);
    List<Reverse> selectIsExit(Integer newid, Integer userid);
    List<Reverse> selectByUserId(Integer integer);
    int numberbynewid(Integer id);
    Reverse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reverse record);

    int updateByPrimaryKey(Reverse record);


}