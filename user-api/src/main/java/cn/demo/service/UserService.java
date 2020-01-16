package cn.demo.service;

import cn.demo.entity.po.User;

import java.util.Map;

public interface UserService {
    User getUserByPhone(String phone);

    void addUser(User u);

    Map<String, Object> checkLogin(String passwd, String phone);
}
