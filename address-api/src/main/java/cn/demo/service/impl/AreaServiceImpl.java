package cn.demo.service.impl;

import cn.demo.dao.AreaDao;
import cn.demo.entity.po.Area;
import cn.demo.service.AreaService;
import cn.util.Constant;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaDao areaDao;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<Map<String, Object>> initArea(Integer id) {
        List list=null;
        list = (List) redisTemplate.opsForHash().get(Constant.AREA, Constant.AREA_ + id);
        if (list==null){
            QueryWrapper wrapper=new QueryWrapper();
            wrapper.eq("cid",id);
            list = areaDao.selectMaps(wrapper);
            redisTemplate.opsForHash().put(Constant.AREA,Constant.AREA_+id,list);
        }
        return list;
    }

    @Override
    public void delByCityIds(List<Integer> cityIds) {
        areaDao.delByCityIds(cityIds);
    }

    @Override
    public void saveManage(Area area) {
        if (area.getId()!=null){
            areaDao.updateById(area);
        }else{
            areaDao.insert(area);
        }
        redisTemplate.delete(Constant.CITYs);
    }

    @Override
    public Area getAreaInfo(Integer id) {
        return areaDao.selectById(id);
    }

    @Override
    public void delThisArea(Integer id) {
        areaDao.deleteById(id);
        redisTemplate.delete(Constant.CITYs);
        redisTemplate.delete(Constant.AREA);
    }

    @Override
    public List<Area> selectAreaByCityId(Integer cityId) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("cid",cityId);
        return areaDao.selectList(wrapper);
    }
}
