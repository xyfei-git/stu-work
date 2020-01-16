package cn.demo.controller;

import cn.util.Constant;
import cn.util.HttpclientUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("user")
@Controller
public class UserCtl {
    @Resource
    private RedisTemplate redisTemplate;


    @RequestMapping("sendCodeWithRegist")
    public Map<String,Object> sendCode(String phone){
        Map<String,Object> map=new HashMap<>();
        try {
            Map<String,String> m=new HashMap<>();
            m.put("phone",phone);
            map = HttpclientUtils.doPost("http://localhost:8084/user/sendCodeWithRegist", m);
            if (map.get("code").equals(200)){
                String data = (String) map.get("data");
                redisTemplate.opsForValue().set(Constant.CODE_+phone,data,60, TimeUnit.SECONDS);
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("messgae",e.getMessage());
        }
        return map;
    }

}
