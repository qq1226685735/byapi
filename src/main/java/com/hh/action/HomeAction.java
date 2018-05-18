package com.hh.action;

import com.hh.service.HomeService;
import com.hh.service.NewService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/api")
@Controller
public class HomeAction {
    @Autowired
    private HomeService homeService;
    @RequestMapping(value = "/get/jokebynumber", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String jokebynumber(int location,int number){
        System.out.print("数字"+location+number);
        JSONObject result=homeService.jokebynumber(location,number);

        return  result.toString();

    }

    @RequestMapping(value = "/get/announcebynumber", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  selectAnnounceByNumber(int location,int number){
        JSONObject result=homeService.selectAnnounce(location,number);

        return  result.toString();

    }
    @RequestMapping(value = "/delete/announcebyid", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  deleteannouncebyid(int announceid){
        JSONObject result=homeService.deleteannouncebyid(announceid);

        return  result.toString();

    }
    @RequestMapping(value = "/delete/jokebyid", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String  deletejokebyid(int jokeid){
        JSONObject result=homeService.deletejokebyid(jokeid);

        return  result.toString();

    }

}
