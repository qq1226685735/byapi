package com.hh.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
@Component
public class JedisTool {
    @Autowired
    private JedisPool jedisPool;//注入JedisPool

    public void set(String token,Map<String, String> value){
        //获取ShardedJedis对象
        Jedis jedis = jedisPool.getResource();
        //存入键值对
        if(jedis.exists(token)){

            System.out.print("已经存在key了");
        }
        jedis.hmset(token,value);
        jedis.expire(token, 3600*240);
        System.out.print(jedis.hvals(token));
        System.out.print(jedis.hgetAll(token));
        //回收ShardedJedis实例
        jedis.close();

    }
    public void setString(String name,String value){
        //获取ShardedJedis对象
        Jedis jedis = jedisPool.getResource();

        jedis.set(name,value);
        jedis.close();

    }
    public String getString(String name){
        Jedis jedis = jedisPool.getResource();
        //根据键值获得数据
      String result=jedis.get(name);
        jedis.close();

        return result;
    }
    public Map get(String token){
        Jedis jedis = jedisPool.getResource();
        //根据键值获得数据
        if(jedis.exists(token)){
        Map<String,String> map= jedis.hgetAll(token);
            jedis.close();
            return map;}
            System.out.print("不存在");
       jedis.close();
        return null;



    }
}
