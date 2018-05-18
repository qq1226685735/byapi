package com.hh.service.impl;

import com.hh.entity.*;
import com.hh.mapper.CommentMapper;
import com.hh.mapper.CommentadmireMapper;
import com.hh.mapper.NewMapper;
import com.hh.mapper.TuserMapper;
import com.hh.service.CommentService;
import com.hh.util.JedisTool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentadmireMapper commentadmireMapper;
    @Autowired
    private TuserMapper tuserMapper;
    @Autowired
    private NewMapper newMapper;
    @Autowired
    private JedisTool jedisTool;
    @Override
    public JSONObject selectNewByNumber(int location, int number,int newid) {
        System.out.print("id"+newid);
        List<Comment> commentlist= commentMapper.selectCommentByNumber(location,number,newid);
        JSONObject obj = new JSONObject();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List datalist=new ArrayList<String>();
        List users=new ArrayList<Tuser>();
        List commentnum=new ArrayList<Integer>();
        for(int i=0;i<commentlist.size();i++){
            String time = formatter.format( commentlist.get(i).getDate());
            datalist.add(time);
            Tuser tuser=tuserMapper.selectByPrimaryKey(commentlist.get(i).getUserid());
            users.add(tuser);
            int num=commentadmireMapper.numberbycommentid(commentlist.get(i).getId());
            commentnum.add(num);
        }
        obj.put("msg","查询成功");
        obj.put("comments",commentlist);
        obj.put("dates",datalist);
        obj.put("users",users);
        obj.put("commentnum",commentnum);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject insertComment(String newid, String comment,String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        if(comment.isEmpty()) {
            obj.put("msg", "评论不能为空");
            obj.put("status", 436);
            return obj;
        }
        String userid= (String) map.get("id");
        Comment  comment1=new Comment();
        comment1.setNewid(Integer.valueOf(newid));
        comment1.setCommentcontent(comment);
        comment1.setDate(new Date());
        comment1.setUserid(Integer.valueOf(userid));

        if(commentMapper.insertSelective(comment1)!=0){
            newMapper.updatecommentnumber(Integer.valueOf(newid));
            obj.put("msg","发表成功");
            obj.put("status",200);
            return obj;
        }
        obj.put("msg","发表失败");
        obj.put("status",437);
        return obj;
    }

    @Override
    public JSONObject addcommentadmire(String commentid, String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        String userid= (String) map.get("id");
        if(commentadmireMapper.selectIsExit(Integer.valueOf(commentid),Integer.valueOf(userid)).isEmpty()){
            Commentadmire commentadmire=new Commentadmire();
            commentadmire.setUserid(Integer.valueOf(userid));
            commentadmire.setCommentid(Integer.valueOf(commentid));
            commentadmire.setDate(new Date());
            if(commentadmireMapper.insertSelective(commentadmire)!=0){
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
    public JSONObject commentbyuserid(int location, int number, String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        String userid= (String) map.get("id");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List datalist=new ArrayList<String>();
        List<CommentWithNew> commentlist=commentMapper.selectByUserId(location,number,Integer.valueOf(userid));
        for(int i=0;i<commentlist.size();i++){
            String time = formatter.format( commentlist.get(i).getDate());
            datalist.add(time);
        }
        obj.put("msg","查询成功");
        obj.put("comments",commentlist);
        obj.put("dates",datalist);
        obj.put("status",200);
        return  obj;
    }

    @Override
    public JSONObject deletebycommentid(int commentid,String token) {
        JSONObject obj = new JSONObject();
        Map map = jedisTool.get(token);
        if(map==null){
            obj.put("msg", "token失效，请重新登录");
            obj.put("status", 435);
            return obj;
        }
        String userid= (String) map.get("id");
        if(commentMapper.deleteByPrimaryKey(commentid)!=0){
            obj.put("msg", "删除成功");
            obj.put("status", 200);
            return obj;
        }
        obj.put("msg", "删除失败");
        obj.put("status", 437);
        return obj;
    }
}
