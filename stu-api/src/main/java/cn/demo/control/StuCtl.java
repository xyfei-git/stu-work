package cn.demo.control;

import cn.demo.entity.ExcelStu;
import cn.demo.entity.po.Stu;
import cn.demo.entity.vo.StuBean;
import cn.demo.service.StuService;
import cn.demo.util.ExcelRefAnno;
import cn.demo.utils.ExcelUtil;
import cn.util.HttpclientUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("stu")
@CrossOrigin
public class StuCtl {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StuCtl.class);

    @Resource
    private StuService stuService;
    @ResponseBody
    @RequestMapping("initStuList")
    public Map<String,Object> initStuList(StuBean stu){
        Map<String,Object> map=new HashMap<>();
        try {
            stu =stuService.initStuList(stu);
            map.put("code",200);
            map.put("message","ok");
            map.put("data",stu);
            log.debug("success");
            int i=0/8;
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("getExportData")
    public Map<String,Object> getExportData(){
        Map<String,Object> map=new HashMap<>();
        try {
            Map<String,Object> data=stuService.getExportData();
            map.put("code",200);
            map.put("message","ok");
            map.put("data",data);
            log.debug("success");
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("fileInput")
    public Map<String,Object> fileInput(@RequestParam(value = "file",required = false) MultipartFile file, HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        try {
            //文件名
            String fielName = file.getOriginalFilename();
            String newFileName= UUID.randomUUID().toString()+fielName.substring(fielName.lastIndexOf("."));
            String path="D:\\myProject\\stu-work\\stu-web\\src\\main\\webapp\\img";
            File f=new File(path);
            if (!f.exists()){
                f.mkdir();
            }
            file.transferTo(new File(path+"\\"+newFileName));
            map.put("data","img/"+newFileName);
            map.put("code",200);
            map.put("message","ok");
            log.debug("success");
        }catch (Exception e){
            log.error(e.getMessage());
            map.put("code",500);
            map.put("message",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("saveManage")
    public Map<String,Object> saveManage(Stu stu){
        Map<String,Object> map=new HashMap<>();
        try {
            stuService.saveManage(stu);
            map.put("code",200);
            map.put("message","ok");
            map.put("data",stu);
            log.debug("success");
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",500);
            log.error(e.getMessage());
            map.put("message",e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("getInfoById")
    public Map<String,Object> getInfoById(Integer id){
        Map<String,Object> map = null;
        try {
            Stu s=stuService.getInfoById(id);
            Map<String,String> m=new HashMap<>();
            m.put("id",s.getProvinceId().toString());
            map = HttpclientUtils.doPost("http://localhost:8083/city/initCity", m);
            m.put("id",s.getCityId().toString());
            Map<String, Object> stringObjectMap = HttpclientUtils.doPost("http://localhost:8083/area/initArea", m);
            if (map.get("code").equals(200) && stringObjectMap.get("code").equals(200)){
                map.put("stu",s);
                map.put("area",JSONObject.parse((String)stringObjectMap.get("data")));
                map.put("city", JSONObject.parse((String)map.get("data")));
                log.debug("success");
            }else{
                log.error(map.get("message").toString());
                log.error(stringObjectMap.get("message").toString());
            }
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("delThis")
    public Map<String,Object> delThis(Integer id){
        Map<String,Object> map=new HashMap<>();
        try {
            stuService.delThis(id);
            map.put("code",200);
            map.put("message","ok");
            log.debug("success");
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("exportExcel")
    public void exportExcel(String field, String coloums, HttpServletResponse response){
        Map<String,Object> map=new HashMap<>();
        Map<String,Object> m=new HashMap<>();
        try {

            List<ExcelStu> list= stuService.getAllStu();
            if (m.size()!=0){
                String[] fields = field.split(",");
                String[] coloum = coloums.split(",");
                List<String> filedes=new ArrayList<>();
                List<String> coloumes=new ArrayList<>();
                for (int i = 0; i < fields.length; i++) {
                    filedes.add(fields[i]);
                    coloumes.add(coloum[i]);
                }
                m.put("fileds",filedes);
                m.put("coloums",coloumes);
                ExcelRefAnno.exportExcelSteam(list, ExcelStu.class,m,response);
            }else{
                ExcelRefAnno.exportExcelSteam(list, ExcelStu.class,null,response);
            }

            log.debug("success");
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
    }

    @RequestMapping("exportExcel2")
    public void exportExcel(HttpServletResponse response){
        List<ExcelStu> list= stuService.getAllStu();
        try {
            ExcelUtil.exportExcel(list,response);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
