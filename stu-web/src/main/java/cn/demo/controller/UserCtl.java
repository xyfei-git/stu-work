package cn.demo.controller;

import cn.util.HttpclientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("user")
@Controller
public class UserCtl {

    @ResponseBody
    @RequestMapping("sendCodeWithRegist")
    public Map<String,Object> sendCode(String phone){
        Map<String,Object> map=new HashMap<>();
        try {
            Map<String,String> m=new HashMap<>();
            m.put("phone",phone);
            map = HttpclientUtils.doPost("http://localhost:8084/user/sendCodeWithRegist", m);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("messgae",e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("registUser")
    public Map<String,Object> registUser(String code,String phone,String passwd){
        Map<String,Object> map=new HashMap<>();
        try {
            Map<String,String> m=new HashMap<>();
            m.put("phone",phone);
            m.put("passwd",passwd);
            m.put("code",code);
            map = HttpclientUtils.doPost("http://localhost:8084/user/registUser", m);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("messgae",e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("checkLogin")
    public Map<String,Object> checkLogin(String phone,String passwd){
        Map<String,Object> map=new HashMap<>();
        try {
            Map<String,String> m=new HashMap<>();
            m.put("phone",phone);
            m.put("passwd",passwd);
            map = HttpclientUtils.doPost("http://localhost:8084/user/checkLogin", m);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("messgae",e.getMessage());
        }
        return map;
    }

}
