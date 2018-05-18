package com.hh.mapper;

import com.hh.entity.New;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NewMapper {
    int deleteByPrimaryKey(Integer id);
    List<New>  selectAllNew();
    List<New> selectNewByNumber(int location,int number);
    List<New> selectNewByLook(int location,int number);
    List<New> selectNewByType(int location, int number, String type);
    New selectByPrimaryKey(int id);
    int insert(New record);
    int insertSelective(New record);
    int updatecommentnumber(Integer id);
    int updatelooknumber(Integer id);
     int updateByPrimaryKeySelective(New record);
    int updateByPrimaryKey(New record);
     List<New> selectByIdScope(int location,int number,int id);

    List<New> selectByUserId(int location, int number, Integer integer);

    List<New> selectByMsg(int location, int number, String msg);

    int selectNewnumber();

    List<New> selectNewByNumberTwo(int location, int number);
}
