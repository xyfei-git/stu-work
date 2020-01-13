package cn.demo.service.impl;

import cn.demo.dao.StuDao;
import cn.demo.entity.ExcelStu;
import cn.demo.entity.po.Stu;
import cn.demo.entity.vo.StuBean;
import cn.demo.service.StuService;
import cn.demo.util.PoiExcelAnnotation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StuServiceImpl implements StuService {

    @Resource
    private StuDao stuDao;

    @Override
    public StuBean initStuList(StuBean stu) {
        Long count=stuDao.selectStuCount(stu);
        stu.setTotal(Integer.valueOf(String.valueOf(count)));
        stu.calculate();
        stu.setList(stuDao.selectStuList(stu));
        return stu;
    }

    @Override
    public void saveManage(Stu stu) {
        if (stu.getId()==null){
            stu.setIsDel(2);
            stuDao.insert(stu);
        }else{
            stuDao.updateById(stu);
        }

    }

    @Override
    public Stu getInfoById(Integer id) {
        return stuDao.selectById(id);
    }

    @Override
    public void delThis(Integer id) {
        stuDao.updateThis(id);
    }

    @Override
    public List<ExcelStu> getAllStu() {
        List<ExcelStu> list=stuDao.getAllStu();
        return list;
    }

    @Override
    public Map<String,Object> getExportData() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("cn.demo.entity.ExcelStu");
        Map<String,Object> map=new HashMap<>();
        Field[] fields=clazz.getDeclaredFields();
        List<String> fileds=new ArrayList<>();
        List<String> coloums=new ArrayList<>();
        for (Field field : fields) {
            PoiExcelAnnotation annotation = field.getAnnotation(PoiExcelAnnotation.class);
            if(annotation != null){
                String name = field.getName();
                String value = annotation.value();
                fileds.add(name);
                coloums.add(value);
            }
        }
        map.put("fileds",fileds);
        map.put("coloums",coloums);
        return map;
    }
}
