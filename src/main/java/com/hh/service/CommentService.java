package com.hh.service;

import net.sf.json.JSONObject;

public interface CommentService {
    JSONObject selectNewByNumber(int location, int number,int newid);

    JSONObject insertComment(String newid, String comment,String token);

    JSONObject addcommentadmire(String commentid, String token);

    JSONObject commentbyuserid(int location, int number, String token);

    JSONObject deletebycommentid(int commentid,String token);
}
