package cn.demo.service;

import cn.demo.entity.po.Province;

import java.util.List;
import java.util.Map;

public interface ProvinceService {


    List<Map<String,Object>>  initProvice();

    void saveManage(Province p);

    Province getThisInfo(Integer id);

    void delThisProvince(Integer id);

    List<Province> getAllProvince();
}
