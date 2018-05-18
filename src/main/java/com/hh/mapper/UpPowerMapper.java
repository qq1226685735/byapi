package com.hh.mapper;

import com.hh.entity.New;
import com.hh.entity.UpPower;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpPowerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UpPower record);

    int insertSelective(UpPower record);

    UpPower selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UpPower record);

    int updateByPrimaryKey(UpPower record);

    List<UpPower> selectPowerByNumber(int location, int number);

    int selectPowernumber();
}