package com.hh.mapper;

import com.hh.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    public User selectUserById(int id);
    public User selectUserByNP(String username,String userpassword);
}
