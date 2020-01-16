package cn.demo.control;

import cn.demo.entity.po.City;
import cn.demo.service.CityService;
import cn.demo.util.ExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("city")
public class CityCtl {
    @Resource
    private CityService cityService;

    @ResponseBody
    @RequestMapping("initCity")
    public Map<String,Object> initCity(Integer id){
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("code",200);
            map.put("message","ok");
            List<Map<String,Object>> list= cityService.initCity(id);
            map.put("data",list);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("saveManage")
    public Map<String,Object> saveManage(City city){
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("code",200);
            map.put("message","ok");
           cityService.saveManage(city);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("getCityInfo")
    public Map<String,Object> getCityInfo(Integer id){
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("code",200);
            map.put("message","ok");
            City m= cityService.getCityInfo(id);
            map.put("data",m);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("delThisCity")
    public Map<String,Object> delThisCity(Integer id){
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("code",200);
            map.put("message","ok");
            cityService.delThisCity(id);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("exportCityExcel")
    public void exportCityExcel(Integer proviceId, HttpServletResponse response){
        try {
            List<City> c=cityService.selectCityByProvince(response,proviceId);
            ExcelUtil.exportExcel(c,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
