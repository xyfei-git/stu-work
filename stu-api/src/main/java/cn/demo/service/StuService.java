package cn.demo.service;

import cn.demo.entity.ExcelStu;
import cn.demo.entity.po.Stu;
import cn.demo.entity.vo.StuBean;

import java.util.List;
import java.util.Map;

public interface StuService {
    StuBean initStuList(StuBean stu);

    void saveManage(Stu stu);

    Stu getInfoById(Integer id);

    void delThis(Integer id);

    List<ExcelStu> getAllStu();

    Map<String,Object> getExportData() throws ClassNotFoundException;
}
