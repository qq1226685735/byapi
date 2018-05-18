package com.hh.service.impl;

import com.hh.entity.Announce;
import com.hh.entity.Joke;
import com.hh.mapper.AnnounceMapper;
import com.hh.mapper.JokeMapper;
import com.hh.service.HomeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private JokeMapper jokeMapper;
    @Autowired
    private AnnounceMapper announceMapper;

    @Override
    public JSONObject jokebynumber(int location, int number) {
        List<Joke> jokelist= jokeMapper.selectJokeByNumber(location,number);
        int  jokenumber=announceMapper.selectAnnouncenumber();
        JSONObject obj = new JSONObject();
        obj.put("msg","查询成功");
        obj.put("jokelist",jokelist);
        obj.put("jokenumber",jokenumber);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject selectAnnounce(int location, int number) {
        List<Announce> announcelist= announceMapper.selectAnnounceByNumber(location,number);
        int  announcenumber=announceMapper.selectAnnouncenumber();
        JSONObject obj = new JSONObject();
        obj.put("msg","查询成功");
        obj.put("announcelist",announcelist);
        obj.put("announcenumber",announcenumber);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject deleteannouncebyid(int announceid) {
        JSONObject obj = new JSONObject();
        if(announceMapper.deleteByPrimaryKey(announceid)!=0){
            obj.put("msg","成功");
            obj.put("status",200);
            return obj;
        }
        obj.put("msg","失败");
        obj.put("status",423);
        return obj;
    }

    @Override
    public JSONObject deletejokebyid(int jokeid) {
        JSONObject obj = new JSONObject();
        if(jokeMapper.deleteByPrimaryKey(jokeid)!=0){
            obj.put("msg","成功");
            obj.put("status",200);
            return obj;
        }
        obj.put("msg","失败");
        obj.put("status",423);
        return obj;
    }
}
