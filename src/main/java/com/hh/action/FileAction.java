package com.hh.action;

import net.sf.json.JSONObject;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
@RequestMapping(value = "/api")
@Controller
public class FileAction {
    @RequestMapping(value = "post/upLoadFile", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String upLoadFile(HttpServletRequest request)
            throws IllegalStateException, IOException {
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
        ArrayList<String> pathList=new ArrayList<String>();
        pathList.add(url);
        JSONObject result= new JSONObject();
        result.put("errno",0);
        result.put("data",pathList);
        return result.toString();
    }
}