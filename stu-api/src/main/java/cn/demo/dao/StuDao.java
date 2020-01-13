package cn.demo.dao;

import cn.demo.entity.ExcelStu;
import cn.demo.entity.po.Stu;
import cn.demo.entity.vo.StuBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface StuDao extends BaseMapper<Stu> {
    List<Map> selectStuList(StuBean stu);

    Long selectStuCount(StuBean stu);

    void updateThis(Integer id);

    List<ExcelStu> getAllStu();
}
