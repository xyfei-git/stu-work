package cn.demo.control;

import cn.demo.entity.po.Area;
import cn.demo.service.AreaService;
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
@RequestMapping("area")
@CrossOrigin
public class AreaCtl {

    @Resource
    private AreaService areaService;
    @ResponseBody
    @RequestMapping("initArea")
    public Map<String,Object> initArea(Integer id){
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("code",200);
            map.put("message","ok");
            List<Map<String,Object>> list= areaService.initArea(id);
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
    public Map<String,Object> saveManage(Area area){
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("code",200);
            map.put("message","ok");
            areaService.saveManage(area);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("getAreainfo")
    public Map<String,Object> getAreainfo(Integer id){
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("code",200);
            map.put("message","ok");
            Area a=areaService.getAreaInfo(id);
            map.put("data",a);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("delThisArea")
    public Map<String,Object> delThisArea(Integer id){
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("code",200);
            map.put("message","ok");
            areaService.delThisArea(id);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("exportAreaExcel")
    public void exportAreaExcel(HttpServletResponse response, Integer cityId){
        try {
            List<Area> a=areaService.selectAreaByCityId(cityId);
            ExcelUtil.exportExcel(a,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
