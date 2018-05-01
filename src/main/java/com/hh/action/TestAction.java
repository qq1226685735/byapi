package com.hh.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.entity.User;
import com.hh.util.JedisTool;
import com.hh.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestAction
{   String jsonStr;
   @Autowired
   private User  user;
   @Autowired
   private JedisTool jedisTool;
    @RequestMapping(value = "test", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    /*public  String Test(){
        ObjectMapper mapper = new ObjectMapper();
        user.setUserName("啦啦");
        user.setUserId(1);
        user.setUserPassword("密码");
        try {
           jsonStr = mapper.writeValueAsString(user);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;

    }*/
    public List test2(){
        Map<String,String> user=new HashMap<>();
        user.put("id","1");
        user.put("name","hh");
        String token=TokenUtil.GetGUID();
        System.out.print(token);
        jedisTool.demo_set(token,user);
        ObjectMapper mapper = new ObjectMapper();

        return  jedisTool.demo_get(token);
    }

}
