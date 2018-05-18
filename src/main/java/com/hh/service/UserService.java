package com.hh.service;

import com.hh.entity.User;
import net.sf.json.JSONObject;

public interface UserService {
   JSONObject selectUserByNP(String username, String password);
   JSONObject insertUser(String username, String password);

   JSONObject updateuser(String username,String password, String sex, String url,String token);

    JSONObject selectAuthorById(String id);

    JSONObject powerapply(int role, String reason, String token);

    JSONObject powerbynumber(int location, int number, String token);

    JSONObject uppowerbyuserid(int powerid, int userid, int role);

    JSONObject denyuppower(int powerid, int userid, int role);

    JSONObject userbynumber(int location, int number, String token);

    JSONObject deleteuserbyid(int userid);
}
