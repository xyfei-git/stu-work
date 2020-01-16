package cn.demo.dao;

import cn.demo.entity.po.Area;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface AreaDao extends BaseMapper<Area> {
    void delByCityIds(List<Integer> cityIds);
}
