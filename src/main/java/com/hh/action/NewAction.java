package com.hh.action;

import com.hh.service.CommentService;
import com.hh.service.NewService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@RequestMapping(value = "/api")
@Controller
public class NewAction {
    @Autowired
    private NewService newService;
    @Autowired
    private CommentService commentService;
    @RequestMapping(value = "/post/addnew", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    //发表新闻
    public String upLoadFile(HttpServletRequest request,String title,String content,String newtype,String token)
            throws IllegalStateException, IOException {
        System.out.print(title);
        System.out.print(content);
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
                        path = path1+'\\'+myFileName;
                        url="http://127.0.0.1:8081/upload/"+myFileName;
                        File localFile = new File(path);
                        f.transferTo(localFile);
                    }
                }
            }

        }
        JSONObject result= newService.insertNew(title,content,url,newtype,token);
        return result.toString();
    }


//    查询所有新闻
@RequestMapping(value = "/get/allnew", method = { RequestMethod.GET,
        RequestMethod.POST }, produces = "application/json;charset=UTF-8")
@ResponseBody
    public  String  selectAllNew(){

        JSONObject result=newService.selectAllNew();
        return  result.toString();

    }
    //查询指定数量，位置新闻

    @RequestMapping(value = "/get/newbynumber", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  selectNewByNumber(int location,int number){
        System.out.print(location+number);
        JSONObject result=newService.selectNewByNumber(location,number);

        return  result.toString();

    }
    @RequestMapping(value = "/get/newbynumbertwo", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  newbynumbertwo(int location,int number){
        System.out.print(location+number);
        JSONObject result=newService.newbynumbertwo(location,number);

        return  result.toString();

    }

    //查询指定数量，位置待审核新闻

    @RequestMapping(value = "/get/newvbynumber", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  selectNewvByNumber(int location,int number){
        System.out.print(location+number);
        JSONObject result=newService.selectNewvByNumber(location,number);

        return  result.toString();

    }
    //查询浏览前几位的新闻轮播

    @RequestMapping(value = "/get/newbylook", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  selectNewByLook(int location,int number){
        System.out.print(location+number);
        JSONObject result=newService.selectNewByLook(location,number);

        return  result.toString();

    }
    //按类型查询新闻

    @RequestMapping(value = "/get/newbytype", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  selectNewByType(int location,int number,String type){
        JSONObject result=newService.selectNewByType(location,number,type);

        return  result.toString();

    }
    //查询指定id新闻
    @RequestMapping(value = "/get/newbyid", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  selectNewById(int newid){
        JSONObject result=newService.selectNewById(newid);

        return  result.toString();

    }
    //查询指定id新闻
    @RequestMapping(value = "/get/newvbyid", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  selectNewvById(int newid){
        JSONObject result=newService.selectNewvById(newid);

        return  result.toString();

    }
    //查询收藏的新闻
    @RequestMapping(value = "/get/newbyreverse", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  selectNewByReverse(int location,int number,String token){
        JSONObject result=newService.selectNewByReverse(location,number,token);

        return  result.toString();

    }

    //查询我的发表
    @RequestMapping(value = "/get/newbyuserid", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  selectNewByUserId(int location,int number,String token){
        JSONObject result=newService.selectNewByUserId(location,number,token);

        return  result.toString();

    }
    @RequestMapping(value = "/get/newbymsg", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  selectNewByMsg(int location,int number,String msg){
        JSONObject result=newService.selectNewByMsg(location,number,msg);

        return  result.toString();

    }

    //点赞
    @RequestMapping(value = "/post/admirenew", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String admirenew(String newid,String token){

        JSONObject result=newService.admirenew(newid,token);

        return  result.toString();

    }
    //点赞
    @RequestMapping(value = "/get/newadmirenum", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String newadmirenum(String newid,String token){

        JSONObject result=newService.newadmirenum(newid,token);

        return  result.toString();

    }

    @RequestMapping(value = "/post/newvtonew", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String newvtonew(String newid,String token){

        JSONObject result=newService.newvtonew(newid,token);

        return  result.toString();

    }
    //收藏
    @RequestMapping(value = "/post/reversenew", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String reversenew(String newid,String token){

        JSONObject result=newService.reversenew(newid,token);

        return  result.toString();

    }
    //收藏数
    @RequestMapping(value = "/get/newreversenum", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String newreversenum(String newid,String token){

        JSONObject result=newService.newreversenum(newid,token);

        return  result.toString();

    }
    @RequestMapping(value = "/get/deletebynewid", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  deletebynewid(int newid,String token){
        JSONObject result=newService.deletebynewid(newid,token);

        return  result.toString();

    }
    @RequestMapping(value = "/delete/newvbyid", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  newvbyid(int newid,String token){
        JSONObject result=newService.newvbyid(newid,token);

        return  result.toString();

    }


}
