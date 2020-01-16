package cn.demo.control;

import cn.demo.entity.po.User;
import cn.demo.service.UserService;
import cn.util.Constant;
import cn.util.sendCode.SendCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("user")
@Controller
public class UserCtl {
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;
    @ResponseBody
    @RequestMapping("sendCodeWithRegist")
    public Map<String,Object> sendCodeWithRegist(String phone){
        Map<String,Object> map=null;
        try {
            if (!StringUtils.isNotBlank(phone)){
                map.put("code",300);
                map.put("message","手机号为空");
            }else{
                User u=userService.getUserByPhone(phone);
                if (u!=null){
                    map.put("code",300);
                    map.put("message","手机号已被注册");
                }else{
                    map = SendCodeUtil.sendCode(phone);
                    if (map.get("code").equals(200)){
                        String data = (String) map.get("data");
                        redisTemplate.opsForValue().set(Constant.CODE_+phone,data,60, TimeUnit.SECONDS);
                    }else{
                        map.put("code",300);
                        map.put("message","发送失败"+map.get("message"));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("registUser")
    public Map<String,Object> registUser(String code,String phone,String passwd){
        Map<String,Object> map=new HashMap<>();
        try {
            if (!StringUtils.isNotBlank(code)){
                map.put("code",300);
                map.put("message","验证码不能为空");
            }else if(!StringUtils.isNotBlank(phone)){
                map.put("code",300);
                map.put("message","手机号不能为空");
            }else  if(!StringUtils.isNotBlank(passwd)){
                map.put("code",300);
                map.put("message","密码不能为空");
            }else{
                String o = (String) redisTemplate.opsForValue().get(Constant.CODE_ + phone);
                if (code.equals(o) && StringUtils.isNotBlank(o)){
                    User u=userService.getUserByPhone(phone);
                    if (u==null){
                        u=new User();
                        u.setPasswd(passwd);
                        u.setPhone(phone);
                        u.setUserName(phone);
                        userService.addUser(u);
                        map.put("code",200);
                        map.put("message","注册成功");
                    }else{
                        map.put("code",300);
                        map.put("message","手机号已被注册");
                    }
                }else{
                    map.put("code",300);
                    map.put("message","验证码不正确");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("checkLogin")
    public Map<String,Object> checkLogin(String phone,String passwd){
        Map<String,Object> map=new HashMap<>();
        try {
            Map<String,String> m=new HashMap<>();
            if (!StringUtils.isNotBlank(phone)){
                map.put("code",300);
                map.put("message","手机号不能为空");
            }else if(!StringUtils.isNotBlank(passwd)){
                map.put("code",300);
                map.put("message","密码不能为空");
            }else{
                map = userService.checkLogin(passwd,phone);
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("messgae",e.getMessage());
        }
        return map;
    }
}
