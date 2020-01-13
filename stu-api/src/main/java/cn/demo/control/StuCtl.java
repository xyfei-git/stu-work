package cn.demo.control;

import cn.demo.entity.ExcelStu;
import cn.demo.entity.po.Stu;
import cn.demo.entity.vo.StuBean;
import cn.demo.service.StuService;
import cn.demo.util.ExcelRefAnno;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("stu")
@CrossOrigin
public class StuCtl {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StuCtl.class);

    @Resource
    private StuService stuService;

    @RequestMapping("initStuList")
    public Map<String,Object> initStuList(StuBean stu){
        Map<String,Object> map=new HashMap<>();
        try {
            stu =stuService.initStuList(stu);
            map.put("code",200);
            map.put("message","ok");
            map.put("data",stu);
            log.debug("success");
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
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
            map.put("data",path+"\\"+newFileName);
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


    @RequestMapping("getInfoById")
    public Map<String,Object> getInfoById(Integer id){
        Map<String,Object> map=new HashMap<>();
        try {
            Stu s=stuService.getInfoById(id);
            map.put("code",200);
            map.put("message","ok");
            map.put("data",s);
            log.debug("success");
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }

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
    public Map<String,Object> exportExcel(){
        Map<String,Object> map=new HashMap<>();
        try {
            List<ExcelStu> list= stuService.getAllStu();
            String s = ExcelRefAnno.exportExcel(list, ExcelStu.class);
            map.put("code",200);
            map.put("message","ok");
            map.put("data",s);
            log.debug("success");
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }
}
