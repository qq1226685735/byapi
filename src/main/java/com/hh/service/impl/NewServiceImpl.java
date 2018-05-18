package com.hh.service.impl;

import com.hh.entity.New;
import com.hh.entity.NewV;
import com.hh.entity.Newadmire;
import com.hh.entity.Reverse;
import com.hh.mapper.NewMapper;
import com.hh.mapper.NewVMapper;
import com.hh.mapper.NewadmireMapper;
import com.hh.mapper.ReverseMapper;
import com.hh.service.NewService;
import com.hh.util.JedisTool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class NewServiceImpl implements NewService {

    @Autowired
    private  NewMapper newMapper;
    @Autowired
    private NewVMapper newvMapper;
    @Autowired
    private NewadmireMapper newadmireMapper;
    @Autowired
    private ReverseMapper reverseMapper;
    @Autowired
    private  New nw;
    @Autowired
    private NewV nwv;
    @Autowired
    private JedisTool jedisTool;


    @Override
    public JSONObject insertNew(String title, String content, String imgpath,String newtype,String token) {

        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 427);
            return obj;
        }
       String permission= (String) map.get("permission");
        String userid= (String) map.get("id");
        int uid=Integer.valueOf(userid);
        System.out.print("id为"+uid);
        int  per=Integer.valueOf(permission);
        System.out.print(permission);

        if(title.isEmpty()) {
            obj.put("msg", "标题不能为空");
            obj.put("status", 429);
            return obj;
        }
        if(content.isEmpty()) {
            obj.put("msg", "内容不能为空");
            obj.put("status", 430);
            return obj;
        }
        if(newtype.isEmpty()) {
            obj.put("msg", "类型不能为空");
            obj.put("status", 431);
            return obj;
        }
        if(per<1){
            obj.put("msg", "权限不足");
            obj.put("status", 432);
            return obj;
        }
        if(per<2){
            nwv.setNewtitle(title);
            nwv.setNewcontent(content);
            nwv.setNewimg(imgpath);
            nwv.setNewtype(newtype);
            Date date = new Date();
            nwv.setDate(date);
            nwv.setCommentnumber(0);
            nwv.setLooknumber(0);
            nwv.setUserid(uid);
            if(newvMapper.insertSelective(nwv)!=0){
                obj.put("msg","发表成功，待审核");
                obj.put("status",200);
                return obj;
            }
            obj.put("msg","发表失败");
            obj.put("status",423);
            return obj;
        }
        nw.setNewtitle(title);
        nw.setNewcontent(content);
        nw.setNewimg(imgpath);
        nw.setNewtype(newtype);
        Date date = new Date();
        nw.setDate(date);
        nw.setCommentnumber(0);
        nw.setLooknumber(0);
        nw.setUserid(uid);
        if(newMapper.insertSelective(nw)!=0){
            obj.put("msg","发表成功");
            obj.put("status",200);
            return obj;
        }
        obj.put("msg","发表失败");
        obj.put("status",423);
        return obj;
    }

    @Override
    public JSONObject selectAllNew() {

       List<New> newlist= (List<New>) newMapper.selectAllNew();
        JSONObject obj = new JSONObject();
        obj.put("msg","查询成功");
        obj.put("news",newlist);
        obj.put("status",200);

        return  obj;
    }

    @Override
    public JSONObject selectNewByNumber(int location, int number) {

        List<New> newlist= newMapper.selectNewByNumber(location,number);
        JSONObject obj = new JSONObject();
        obj.put("msg","查询成功");
        obj.put("news",newlist);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject selectNewByLook(int location, int number) {

        List<New> newlist= newMapper.selectNewByLook(location,number);
        JSONObject obj = new JSONObject();
        obj.put("msg","查询成功");
        obj.put("news",newlist);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject selectNewById(int newid) {
         nw=newMapper.selectByPrimaryKey(newid);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String puttime = formatter.format(nw.getDate());
        JSONObject obj = new JSONObject();
        if(nw!=null){
            newMapper.updatelooknumber(newid);
         obj.put("msg","查询成功");
        obj.put("new",nw);
        obj.put("puttime",puttime);
        obj.put("status",200);
        return  obj;
        }
        obj.put("msg","查询失败");
        obj.put("new","");
        obj.put("status",424);
        return  obj;

    }

    @Override
    public JSONObject admirenew(String newid,String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        String userid= (String) map.get("id");
        if(newadmireMapper.selectIsExit(Integer.valueOf(newid),Integer.valueOf(userid)).isEmpty()){
            Newadmire newadmire=new Newadmire();
            newadmire.setUserid(Integer.valueOf(userid));
            newadmire.setNewid(Integer.valueOf(newid));
            newadmire.setDate(new Date());
            if(newadmireMapper.insertSelective(newadmire)!=0){
                obj.put("msg","点赞成功");
                obj.put("status",200);
                return obj;
            }
            obj.put("msg","点赞失败");
            obj.put("status",423);
            return obj;


        }
        obj.put("msg", "您已经点过赞了");
        obj.put("status", 423);
        return obj;

    }

    @Override
    public JSONObject newadmirenum(String newid,String token) {
        JSONObject obj = new JSONObject();
        int number=newadmireMapper.numberbynewid(Integer.valueOf(newid));
        obj.put("number",number);
        obj.put("msg","获取点赞成功");
        obj.put("status",200);
        return obj;
    }

    @Override
    public JSONObject reversenew(String newid, String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        String userid= (String) map.get("id");
        if(reverseMapper.selectIsExit(Integer.valueOf(newid),Integer.valueOf(userid)).isEmpty()){
            Reverse reverse=new Reverse();
            reverse.setUserid(Integer.valueOf(userid));
            reverse.setNewid(Integer.valueOf(newid));
            reverse.setDate(new Date());
            if(reverseMapper.insertSelective(reverse)!=0){
                obj.put("msg","收藏成功");
                obj.put("status",200);
                return obj;
            }
            obj.put("msg","收藏失败");
            obj.put("status",423);
            return obj;


        }
        obj.put("msg", "您已经收藏了");
        obj.put("status", 423);
        return obj;

    }

    @Override
    public JSONObject newreversenum(String newid, String token) {
        JSONObject obj = new JSONObject();
        int number=reverseMapper.numberbynewid(Integer.valueOf(newid));
        obj.put("number",number);
        obj.put("msg","获取收藏数成功");
        obj.put("status",200);
        return obj;
    }

    @Override
    public JSONObject selectNewByType(int location, int number, String type) {
    if(type.equals("推荐")){
        List<New> newlist= newMapper.selectNewByLook(location,number);
        System.out.print(newlist);
        System.out.print(type);
        JSONObject obj = new JSONObject();
        obj.put("msg","查询成功");
        obj.put("news",newlist);
        obj.put("status",200);
        return  obj;
    }
        List<New> newlist= newMapper.selectNewByType(location,number,type);
        System.out.print(newlist);
        System.out.print(type);
        JSONObject obj = new JSONObject();
        obj.put("msg","查询成功");
        obj.put("news",newlist);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject selectNewByReverse(int location, int number, String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        String userid= (String) map.get("id");
        List<New> newlist=newMapper.selectByIdScope(location,number,Integer.valueOf(userid));
        obj.put("msg","查询成功");
        obj.put("news",newlist);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject selectNewByUserId(int location, int number, String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        String userid= (String) map.get("id");
        List<New> newlist=newMapper.selectByUserId(location,number,Integer.valueOf(userid));
        obj.put("msg","查询成功");
        obj.put("news",newlist);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject selectNewByMsg(int location, int number, String msg) {
        JSONObject obj = new JSONObject();
        List<New> newlist=newMapper.selectByMsg(location,number,msg);
        obj.put("msg","查询成功");
        obj.put("news",newlist);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject deletebynewid(int newid, String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        String userid= (String) map.get("id");
        if(newMapper.deleteByPrimaryKey(newid)!=0){
            obj.put("msg", "删除成功");
            obj.put("status", 200);
            return obj;
        }
        obj.put("msg", "删除失败");
        obj.put("status", 437);
        return obj;
    }

    @Override
    public JSONObject selectNewvByNumber(int location, int number) {
        List<NewV> newvlist= newvMapper.selectNewvByNumber(location,number);
        JSONObject obj = new JSONObject();
        obj.put("msg","查询成功");
        obj.put("news",newvlist);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject selectNewvById(int newid) {
        NewV nwv=newvMapper.selectByPrimaryKey(newid);

        JSONObject obj = new JSONObject();
        if(nwv!=null){
            obj.put("msg","查询成功");
            obj.put("new",nwv);
            obj.put("status",200);
            return  obj;
        }
        obj.put("msg","查询失败");
        obj.put("new","");
        obj.put("status",424);
        return  obj;

    }

    @Override
    public JSONObject newvtonew(String newid, String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        NewV nwv=newvMapper.selectByPrimaryKey(Integer.valueOf(newid));
        New nw=new New();
        nw.setDate(nwv.getDate());
        nw.setNewtitle(nwv.getNewtitle());
        nw.setNewcontent(nwv.getNewcontent());
        nw.setNewimg(nwv.getNewimg());
        nw.setUserid(nwv.getUserid());
        nw.setNewtype(nwv.getNewtype());
        if(newMapper.insertSelective(nw)!=0){

            newvMapper.deleteByPrimaryKey(Integer.valueOf(newid));
            obj.put("msg","成功");
            obj.put("status",200);
            return obj;
        }
        obj.put("msg","失败");
        obj.put("status",423);
        return obj;
    }

    @Override
    public JSONObject newvbyid(int newid, String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        if(newvMapper.deleteByPrimaryKey(newid)!=0){


            obj.put("msg","成功");
            obj.put("status",200);
            return obj;
        }
        obj.put("msg","失败");
        obj.put("status",423);
        return obj;
    }

    @Override
    public JSONObject newbynumbertwo(int location, int number) {
        List<New> newlist= newMapper.selectNewByNumberTwo(location,number);
        int  newnumber=newMapper.selectNewnumber();
        JSONObject obj = new JSONObject();
        obj.put("msg","查询成功");
        obj.put("newlist",newlist);
        obj.put("newnumber",newnumber);
        obj.put("status",200);
        return  obj;
    }
}

