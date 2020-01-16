package cn.demo.service.impl;

import cn.demo.dao.AreaDao;
import cn.demo.dao.CityDao;
import cn.demo.entity.po.City;
import cn.demo.service.CityService;
import cn.util.Constant;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public class CityServiceImpl implements CityService {

    @Resource
    private CityDao cityDao;

    @Resource
    private AreaDao areaDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<Map<String, Object>> initCity(Integer id) {
        List list=null;
        list= (List) redisTemplate.opsForHash().get(Constant.CITYs,Constant.CITY_+id);
        if (list==null){
            QueryWrapper wrapper=new QueryWrapper();
            wrapper.eq("pid",id);
            list = cityDao.selectMaps(wrapper);
            redisTemplate.opsForHash().put(Constant.CITYs,Constant.CITY_+id,list);
        }

        return list;
    }

    @Override
    public void delByProduce(Integer id) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("pid",id);
        cityDao.delete(wrapper);
        redisTemplate.delete(Constant.CITYs);
    }

    @Override
    public void saveManage(City city) {
        if (city.getId()==null){
            cityDao.insert(city);
        }else{
            cityDao.updateById(city);
        }
        redisTemplate.delete(Constant.CITYs);
    }

    @Override
    public City getCityInfo(Integer id) {
        return cityDao.selectById(id);
    }

    @Override
    public void delThisCity(Integer id) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("cid",id);
        areaDao.delete(wrapper);
        cityDao.deleteById(id);
        redisTemplate.delete(Constant.CITYs);
    }

    @Override
    public List<City> selectCityByProvince(HttpServletResponse response, Integer proviceId) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("pid", proviceId);
        List list = cityDao.selectList(wrapper);
        return list;
    }


}
