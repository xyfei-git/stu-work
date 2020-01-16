package cn.demo.service;

import cn.demo.entity.po.Area;

import java.util.List;
import java.util.Map;

public interface AreaService {
    List<Map<String, Object>> initArea(Integer id);

    void delByCityIds(List<Integer> cityIds);

    void saveManage(Area area);

    Area getAreaInfo(Integer id);

    void delThisArea(Integer id);

    List<Area> selectAreaByCityId(Integer cityId);
}
