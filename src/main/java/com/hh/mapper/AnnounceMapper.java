package com.hh.mapper;

import com.hh.entity.Announce;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnounceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Announce record);

    int insertSelective(Announce record);
    List<Announce> selectAnnounceByNumber(int location, int number);
    Announce selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Announce record);

    int updateByPrimaryKey(Announce record);

    int selectAnnouncenumber();
}