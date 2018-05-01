package com.hh.service;

import com.hh.entity.User;
import net.sf.json.JSONObject;

public interface UserService {
    User selectUserById(Integer userId);
   JSONObject selectUserByNP(String username, String userpassword);

}
