package cn.demo.control;

import cn.demo.entity.po.User;
import cn.demo.service.UserService;
import cn.util.sendCode.GetCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("user")
@Controller
public class UserCtl {
    @Resource
    private UserService userService;

    @RequestMapping("sendCodeWithRegist")
    public Map<String,Object> sendCodeWithRegist(String phone){
        Map<String,Object> map=new HashMap<>();
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
                    String s = GetCodeUtil.sendMessage("11111", phone);
                    System.out.println(s);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }

}
