package cn.demo.control;

import cn.demo.entity.po.Province;
import cn.demo.service.ProvinceService;
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
@RequestMapping("province")
@CrossOrigin
public class ProvinceCtl {

    @Resource
    private ProvinceService provinceService;
    @ResponseBody
    @RequestMapping("initProvice")
    public Map<String,Object> initProvice(){
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("code",200);
            map.put("message","ok");
            List<Map<String,Object>>  list= provinceService.initProvice();
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
    public Map<String,Object> saveManage(Province p){
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("code",200);
            map.put("message","ok");
           provinceService.saveManage(p);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("getThisInfo")
    public Map<String,Object> getThisInfo(Integer id){
        Map<String,Object> map=new HashMap<>();

        try {
            if (id!=null){
                map.put("code",200);
                map.put("message","ok");
                Province p=provinceService.getThisInfo(id);
                map.put("data",p);
            }else{
                map.put("code",300);
                map.put("message","数据为空");
            }


        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("delThisProvince")
    public Map<String,Object> delThisProvince(Integer id){
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("code",200);
            map.put("message","ok");
            provinceService.delThisProvince(id);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("exportProvinceExcel")
    public void exportProvinceExcel(HttpServletResponse response){
        try {
            List<Province> provinces=provinceService.getAllProvince();
            ExcelUtil.exportExcel(provinces,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
