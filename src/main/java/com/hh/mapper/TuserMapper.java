package com.hh.mapper;

import com.hh.entity.Tuser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TuserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tuser record);

    int insertSelective(Tuser record);

    Tuser selectByPrimaryKey(Integer id);
    List<Tuser> selectUserByNP(String username, String password);
    List<Tuser> selectUserByName(String username);
    int updateByPrimaryKeySelective(Tuser record);

    int updateByPrimaryKey(Tuser record);

    List<Tuser> selectUserByNumber(int location, int number);

    int selectUsernumber();
}