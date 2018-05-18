package com.hh.action;

import com.hh.service.CommentService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RequestMapping(value = "/api")
@Controller
public class CommentAction {
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/post/addcomment", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  comment(String newid,String comment,String token) throws UnsupportedEncodingException {

      /*  String comment1 = new String(comment.getBytes("ISO-8859-1"), "UTF-8");*/
        JSONObject result=commentService.insertComment(newid,comment,token);
        return  result.toString();
    }
    @RequestMapping(value = "/get/commentbynumber", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  selectNewByNumber(int location,int number,int newid){
        System.out.print(location+number);

        JSONObject result=commentService.selectNewByNumber(location,number,newid);

        return  result.toString();

    }
    @RequestMapping(value = "/post/admirecomment", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  addcomment(String commentid,String token){
        System.out.print(commentid);

        JSONObject result=commentService.addcommentadmire(commentid,token);

        return  result.toString();

    }
    @RequestMapping(value = "/get/commentbyuserid", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  commentbyuserid(int location,int number,String token){
        JSONObject result=commentService.commentbyuserid(location,number,token);

        return  result.toString();

    }
    @RequestMapping(value = "/get/deletebycommentid", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  deletebycommentid(int commentid,String token){
        JSONObject result=commentService.deletebycommentid(commentid,token);

        return  result.toString();

    }


}
