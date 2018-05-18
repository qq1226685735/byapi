package com.hh.service;

import net.sf.json.JSONObject;

public interface NewService {
    JSONObject insertNew(String title, String content, String imgpath,String newtype,String token);
    JSONObject selectAllNew();
    JSONObject selectNewByNumber(int location,int number);
    JSONObject selectNewByLook(int location, int number);
    JSONObject selectNewById(int newid);

    JSONObject admirenew(String newid,String token);

    JSONObject newadmirenum(String newid,String token);

    JSONObject reversenew(String newid,String token);
    JSONObject newreversenum(String newid,String token);
    JSONObject selectNewByType(int location, int number, String type);

    JSONObject selectNewByReverse(int location, int number, String token);

    JSONObject selectNewByUserId(int location, int number, String token);

    JSONObject selectNewByMsg(int location, int number, String msg);

    JSONObject deletebynewid(int newid, String token);

    JSONObject selectNewvByNumber(int location, int number);

    JSONObject selectNewvById(int newid);

    JSONObject newvtonew(String newid, String token);

    JSONObject newvbyid(int newid, String token);

    JSONObject newbynumbertwo(int location, int number);
}
