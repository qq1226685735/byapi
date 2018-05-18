package com.hh.mapper;

import com.hh.entity.Comment;
import com.hh.entity.CommentWithNew;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);
    List<Comment> selectCommentByNumber(int location, int number,int newid);
    List<CommentWithNew> selectByUserId(int location, int number, int userid);
    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}