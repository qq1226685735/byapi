package com.hh.service.impl;
import com.hh.entity.User;
import com.hh.mapper.UserMapper;
import com.hh.service.UserService;
import com.hh.util.JedisTool;
import com.hh.util.TokenUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisTool jedisTool;
    public User selectUserById(Integer userId) {
        return userMapper.selectUserById(userId);

    }

    @Override
    public JSONObject selectUserByNP(String username, String userpassword) {
        System.out.print(username);
        System.out.print(userpassword);
        JSONObject obj = new JSONObject();
        User loginUser=userMapper.selectUserByNP(username,userpassword);
       if(loginUser==null){
           obj.put("msg","登录失败");
           return obj;
       }
          String token=TokenUtil.GetGUID();
          Map<String,String> user=new HashMap<>();
          user.put("username",loginUser.getUserName());
          String userId= String.valueOf(loginUser.getUserId());
          user.put("id",userId);
          jedisTool.demo_set(token,user);
          obj.put("msg","登录成功");
          obj.put("user",loginUser);
          obj.put("token",token);
            return obj;



    }
}
