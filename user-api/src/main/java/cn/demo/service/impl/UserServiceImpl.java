package cn.demo.service.impl;

import cn.demo.dao.UserDao;
import cn.demo.entity.po.User;
import cn.demo.service.UserService;
import cn.util.Constant;
import cn.util.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public User getUserByPhone(String phone) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("phone",phone);
        return userDao.selectOne(wrapper);
    }

    @Override
    public void addUser(User u) {
        userDao.insert(u);
    }

    @Override
    public Map<String, Object> checkLogin(String passwd, String phone) {
        Map<String,Object> map=new HashMap<>();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("phone",phone);
        User user = userDao.selectOne(wrapper);
        if (user!=null){
            String passwd1 = user.getPasswd();
            if (passwd.equals(passwd1)){
                //登录成功
                String sign = JWT.sign(user, 1000 * 60 * 60 * 24);
                redisTemplate.opsForValue().set(Constant.TOKEN_LOGIN_+phone,sign,1000*60*30, TimeUnit.SECONDS);
                map.put("code",200);
                map.put("message","登录成功");
            }else{
                map.put("code",300);
                map.put("message","账号或密码不正确");
            }
        }else{
            map.put("code",300);
            map.put("message","账号或密码不存在");
        }
        return map;
    }
}
