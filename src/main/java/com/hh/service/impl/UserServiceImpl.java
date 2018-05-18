package com.hh.service.impl;
import com.hh.entity.*;
import com.hh.mapper.TuserMapper;
import com.hh.mapper.UpPowerMapper;
import com.hh.service.UserService;
import com.hh.util.JedisTool;
import com.hh.util.TokenUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TuserMapper tuserMapper;
    @Autowired
    private UpPowerMapper upPowerMapper;
    @Autowired
    private JedisTool jedisTool;
    @Autowired
    private Tuser user;

    @Override
    public JSONObject selectUserByNP(String username, String password) {
        System.out.print(username);
        System.out.print(password);
        JSONObject obj = new JSONObject();
        if(username.isEmpty()) {
            obj.put("msg", "用户名不能为空");
            obj.put("status", 429);
            return obj;
        }
        if(password.isEmpty()) {
            obj.put("msg", "密码不能为空");
            obj.put("status", 430);
            return obj;
        }
        List<Tuser> loginUser = tuserMapper.selectUserByNP(username, password);
        if (loginUser.isEmpty()) {
            obj.put("msg", "登录失败，用户名或密码错误");
            obj.put("status", 420);
            return obj;
        }
        String token = TokenUtil.GetGUID();
        Map<String, String> user = new HashMap<>();
        user.put("password", loginUser.get(0).getPassword());
        user.put("username", loginUser.get(0).getUsername());
        user.put("id", String.valueOf(loginUser.get(0).getId()));
        user.put("permission", String.valueOf(loginUser.get(0).getPermission()));
        jedisTool.set(token, user);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createtime = formatter.format(loginUser.get(0).getCreateTime());
        System.out.print(createtime);
        obj.put("createtime", createtime);
        obj.put("msg", "登录成功");
        obj.put("user", loginUser.get(0));
        obj.put("token", token);
        obj.put("status", 200);
        return obj;


    }

    @Override
    public JSONObject insertUser(String username, String password) {
        System.out.print("密码" + password);

        String name = username;
        String word = password;
        JSONObject obj = new JSONObject();
        if(username.isEmpty()) {
            obj.put("msg", "用户名不能为空");
            obj.put("status", 429);
            return obj;
        }
        if(password.isEmpty()) {
            obj.put("msg", "密码不能为空");
            obj.put("status", 430);
            return obj;
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setPermission(0);
        Date date = new Date();
        user.setCreateTime(date);
        if (tuserMapper.selectUserByName(username).isEmpty()) {
            if (tuserMapper.insertSelective(user) != 0) {
                String token = TokenUtil.GetGUID();
                Map<String, String> user1 = new HashMap<>();
                List<Tuser> loginuser = tuserMapper.selectUserByNP(username, password);
                user1.put("password", loginuser.get(0).getPassword());
                user1.put("username", loginuser.get(0).getUsername());
                user1.put("id", String.valueOf(loginuser.get(0).getId()));
                user1.put("permission", String.valueOf(loginuser.get(0).getPermission()));
                jedisTool.set(token, user1);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createtime = formatter.format(loginuser.get(0).getCreateTime());
                obj.put("createtime", createtime);
                obj.put("user", loginuser.get(0));
                obj.put("msg", "注册成功");
                obj.put("token", token);
                obj.put("status", 200);
                return obj;
            }
            obj.put("msg", "注册失败");
            obj.put("status", 422);
            return obj;
        }
        obj.put("msg", "用户名以存在");
        obj.put("status", 424);
        return obj;
    }

    @Override
    public JSONObject updateuser(String username, String password, String sex, String url, String token) {
        JSONObject obj = new JSONObject();
        if (tuserMapper.selectUserByName(username).isEmpty()||username.isEmpty()) {


            Map map = jedisTool.get(token);
          if(map==null){
              obj.put("msg", "token失效，请重新登录");
              obj.put("status", 427);
              return obj;
          }
            String id = (String) map.get("id");
            System.out.print(Integer.valueOf(id));
            Tuser user = tuserMapper.selectByPrimaryKey(Integer.valueOf(id));
            user.setUsername(username);
            user.setPassword(password);
            user.setSex(sex);
            user.setUserimg(url);

            if (tuserMapper.updateByPrimaryKeySelective(user) == 1) {
                user = tuserMapper.selectByPrimaryKey(Integer.valueOf(id));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createtime = formatter.format(user.getCreateTime());
                obj.put("msg", "更改成功");
                obj.put("status", 200);
                obj.put("user", user);
                obj.put("createtime", createtime);
                return obj;
            }
            obj.put("msg", "更改失败");
            obj.put("status", 425);
            return obj;
        } else {
            obj.put("msg", "已经存在用户名，请换个用户名");
            obj.put("status", 426);
            return obj;
        }


    }

    @Override
    public JSONObject selectAuthorById(String id) {
        JSONObject obj = new JSONObject();
        user = tuserMapper.selectByPrimaryKey(Integer.valueOf(id));
        System.out.print(user.getUsername());
        if(user!=null) {
            obj.put("username",user.getUsername());
            obj.put("userimg",user.getUserimg());
            obj.put("msg", "获取作者成功");
            obj.put("status", 200);
            return obj;
        }
        obj.put("msg", "获取作者失败");
        obj.put("status", 433);
        return obj;
    }

    @Override
    public JSONObject powerapply(int role, String reason, String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        String userid= (String) map.get("id");
        UpPower upPower=new UpPower();
        upPower.setRole(role);
        upPower.setReason(reason);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createtime = formatter.format(new Date());
        upPower.setDate(createtime);
        upPower.setUserid(Integer.valueOf(userid));
        if(upPowerMapper.insertSelective(upPower)!=0) {
            obj.put("msg", "申请成功");
            obj.put("status", 200);
            return obj;
        }
        obj.put("msg", "申请失败");
        obj.put("status", 422);
        return obj;
    }

    @Override
    public JSONObject powerbynumber(int location, int number, String token) {
        List<UpPower> powerlist=upPowerMapper.selectPowerByNumber(location,number);
        int  powernumber=upPowerMapper.selectPowernumber();
        JSONObject obj = new JSONObject();
        obj.put("msg","查询成功");
        obj.put("powerlist",powerlist);
        obj.put("powernumber",powernumber);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject uppowerbyuserid(int powerid, int userid, int role) {
        JSONObject obj = new JSONObject();
        Tuser user=tuserMapper.selectByPrimaryKey(userid);
        user.setPermission(role);
        if(tuserMapper.updateByPrimaryKeySelective(user)!=0){
           UpPower upPower=upPowerMapper.selectByPrimaryKey(powerid);
           upPower.setStatus(0);
           upPowerMapper.updateByPrimaryKeySelective(upPower);
            obj.put("msg","成功");
            obj.put("status",200);
            return obj;
        }
        obj.put("msg","失败");
        obj.put("status",423);
        return obj;
    }

    @Override
    public JSONObject denyuppower(int powerid, int userid, int role) {
        JSONObject obj = new JSONObject();
        UpPower upPower=upPowerMapper.selectByPrimaryKey(powerid);
        upPower.setStatus(0);
        if(upPowerMapper.updateByPrimaryKeySelective(upPower)!=0){
        obj.put("msg","成功");
        obj.put("status",200);
        return obj;
    }
        obj.put("msg","失败");
        obj.put("status",423);
        return obj;
    }

    @Override
    public JSONObject userbynumber(int location, int number, String token) {
        List<Tuser> userlist=tuserMapper.selectUserByNumber(location,number);
        int  usernumber=tuserMapper.selectUsernumber();
        JSONObject obj = new JSONObject();
        obj.put("msg","查询成功");
        obj.put("userlist",userlist);
        obj.put("usernumber",usernumber);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject deleteuserbyid(int userid) {
        JSONObject obj = new JSONObject();
        System.out.print(userid);
        if(tuserMapper.deleteByPrimaryKey(userid)!=0){
            obj.put("msg","成功");
            obj.put("status",200);
            return obj;
        }
        obj.put("msg","失败");
        obj.put("status",423);
        return obj;
    }

}
