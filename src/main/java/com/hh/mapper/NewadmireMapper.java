package com.hh.mapper;

import com.hh.entity.Newadmire;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewadmireMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Newadmire record);

    int insertSelective(Newadmire record);

    Newadmire selectByPrimaryKey(Integer id);
     List<Newadmire> selectIsExit(Integer newid, Integer userid);
    int numberbynewid(Integer id);
    int updateByPrimaryKeySelective(Newadmire record);

    int updateByPrimaryKey(Newadmire record);
}