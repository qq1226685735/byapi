package com.hh.action;

import com.hh.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api")
public class UserAction {
   @Autowired
   private UserService userService;
    @RequestMapping("/User")
    @ResponseBody
    public  String selectUserById(){
        //调用UserMapper方法
       return userService.selectUserById(1).getUserName();
    }

    @RequestMapping(value = "/get/login", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String login(String username,String userpassword){
        //调用UserMapper方法
        JSONObject result=userService.selectUserByNP(username,userpassword);
        return  result.toString();
    }
}
