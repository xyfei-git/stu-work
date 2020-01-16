package cn.demo.service;

import cn.demo.entity.po.User;

public interface UserService {
    User getUserByPhone(String phone);
}
