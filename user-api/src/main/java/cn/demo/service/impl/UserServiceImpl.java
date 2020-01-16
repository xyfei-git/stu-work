package cn.demo.service.impl;

import cn.demo.dao.UserDao;
import cn.demo.entity.po.User;
import cn.demo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Override
    public User getUserByPhone(String phone) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("phone",phone);
        return userDao.selectOne(wrapper);
    }
}
