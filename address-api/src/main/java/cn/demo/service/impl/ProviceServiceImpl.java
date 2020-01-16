package cn.demo.service.impl;

import cn.demo.dao.ProvinceDao;
import cn.demo.entity.po.Province;
import cn.demo.service.AreaService;
import cn.demo.service.CityService;
import cn.demo.service.ProvinceService;
import cn.util.Constant;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProviceServiceImpl implements ProvinceService {

    @Resource
    private ProvinceDao provinceDao;

    @Resource
    private CityService cityService;

    @Resource
    private AreaService areaService;

    @Resource
    private RedisTemplate redisTemplate;


    @Override
    public List<Map<String,Object>> initProvice() {
        List<Map<String, Object>> maplist=null;
        maplist= (List<Map<String, Object>>) redisTemplate.opsForValue().get(Constant.PROVINCE);
        if (maplist==null){
            maplist = provinceDao.selectMaps(null);
            redisTemplate.opsForValue().set(Constant.PROVINCE,maplist);
        }

        return maplist;
    }

    @Override
    public void saveManage(Province p) {
        if (p.getId()!=null){
            provinceDao.updateById(p);
        }else{
            provinceDao.insert(p);
        }
        redisTemplate.delete(Constant.PROVINCE);
    }

    @Override
    public Province getThisInfo(Integer id) {
        Province province = provinceDao.selectById(id);
        return province;
    }

    @Override
    public void delThisProvince(Integer id) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("id",id);
        provinceDao.delete(wrapper);
        List<Map<String, Object>> mapList = cityService.initCity(id);
        cityService.delByProduce(id);
        List<Integer> cityIds=new ArrayList<>();
        if (mapList!=null){
            for (int i = 0; i < mapList.size(); i++) {
                cityIds.add((Integer)mapList.get(i).get("id"));
            }
        }
        areaService.delByCityIds(cityIds);
        redisTemplate.delete(Constant.PROVINCE);
        redisTemplate.delete(Constant.CITYs);
        redisTemplate.delete(Constant.AREA);
    }

    @Override
    public List<Province> getAllProvince() {
        List<Province> provinces = provinceDao.selectList(null);
        return provinces;
    }
}
