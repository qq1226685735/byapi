package com.hh.action;

import com.hh.entity.Tuser;
import com.hh.service.UserService;
import com.hh.util.JedisTool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@Controller
@RequestMapping("/api")
public class UserAction {
   @Autowired
   private UserService userService;
    @Autowired
    private JedisTool jedisTool;

    @RequestMapping(value = "/get/login", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String login(String username,String password,String code,HttpServletRequest request){
        //调用UserMapper方法
        JSONObject result= new JSONObject();
      /*  Cookie[] cookies = request.getCookies();
        String uid="";
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("Code")){
                uid= cookie.getValue();
                System.out.print(uid);
            }
        }*/
        String ucode= (String) request.getSession().getAttribute("ucode");

        System.out.print("验证码"+ucode);
        if(ucode.equals(code)){
         result=userService.selectUserByNP(username,password);
        return  result.toString();}
        result.put("msg","验证码错误");
        result.put("status",421);
        return  result.toString();


    }
    @RequestMapping(value = "/post/register", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String register(String username,String password,String code,HttpServletRequest request){
        JSONObject result= new JSONObject();
        String ucode= (String) request.getSession().getAttribute("ucode");
        if(ucode.equals(code)) {
            result=userService.insertUser(username, password);
            return result.toString();
        }
        result.put("msg","验证码错误");
        result.put("status",421);
        return  result.toString();
    }
    @RequestMapping(value = "/post/powerapply", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String powerapply(int role,String reason,String token){
        JSONObject result=userService.powerapply(role,reason,token);

        return  result.toString();
    }

    @RequestMapping(value = "/get/powerbynumber", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String powerbynumber(int location,int number,String token){
        JSONObject result=userService.powerbynumber(location,number,token);
        return  result.toString();
    }
    @RequestMapping(value = "/get/userbynumber", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String userbynumber(int location,int number,String token){
        JSONObject result=userService.userbynumber(location,number,token);
        return  result.toString();
    }

    @RequestMapping(value = "/get/author", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String getauthor(String userid){

      JSONObject result=userService.selectAuthorById(userid);
        return  result.toString();
    }
    @RequestMapping(value = "/put/uppowerbyuserid", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String uppowerbyuserid(int powerid,int userid,int role){

        JSONObject result=userService.uppowerbyuserid(powerid,userid,role);
        return  result.toString();
    }
    @RequestMapping(value = "/put/denyuppower", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String denyuppower(int powerid,int userid,int role){

        JSONObject result=userService.denyuppower(powerid,userid,role);
        return  result.toString();
    }
    @RequestMapping(value = "/delete/userbyid", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String deleteuserbyid(int userid){

        JSONObject result=userService.deleteuserbyid(userid);
        return  result.toString();
    }

    @RequestMapping(value = "/post/updateuser", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String updateuser(HttpServletRequest request, String username, String sex,String token,String password)
            throws IllegalStateException, IOException {
        System.out.print(username);
        System.out.print(sex);
        String path="";
        String url="";
        // @RequestParam("file") MultipartFile file,
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile f = multiRequest.getFile(iter.next());
                if (f != null) {
                    // 取得当前上传文件的文件名称
                    String myFileName = f.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        String path1 = request.getServletContext().getRealPath("/upload/");
                        System.out.print(path1);
                        // 定义上传路径
                        path = path1+'\\'+username+myFileName;
                        url="http://127.0.0.1:8081/upload/"+username+myFileName;
                        File localFile = new File(path);
                        f.transferTo(localFile);
                    }
                }
            }

        }

        JSONObject result=userService.updateuser(username,password,sex,url,token);
        return result.toString();
    }
}
