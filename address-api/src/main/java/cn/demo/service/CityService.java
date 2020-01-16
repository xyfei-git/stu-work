package cn.demo.service;

import cn.demo.entity.po.City;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface CityService {
    List<Map<String, Object>> initCity(Integer id);

    void delByProduce(Integer id);

    void saveManage(City city);

    City getCityInfo(Integer id);

    void delThisCity(Integer id);

    List<City> selectCityByProvince(HttpServletResponse response, Integer proviceId);
}
