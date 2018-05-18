package com.hh.mapper;

import com.hh.entity.Commentadmire;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentadmireMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Commentadmire record);

    int insertSelective(Commentadmire record);

    Commentadmire selectByPrimaryKey(Integer id);
    List<Commentadmire> selectIsExit(Integer commentid, Integer userid);
    int numberbycommentid(Integer id);
    int updateByPrimaryKeySelective(Commentadmire record);

    int updateByPrimaryKey(Commentadmire record);
}