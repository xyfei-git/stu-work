package cn.demo.util;

import java.util.Date;

@PoiExcelAnnotation(title = "用户信息展示",sheetName = "用户信息",mkdir = "commons/excel")
public class UserPoiBeanVo {

    @PoiExcelAnnotation("用户姓名")
    private String name;
    @PoiExcelAnnotation("用户年龄")
    private Integer age;

    @PoiExcelAnnotation("用户性别")
    private String sex;
    @PoiExcelAnnotation("出生日期")
    private Date birthday;
    @PoiExcelAnnotation("部门名称")
    private String deptName;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }



    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }



}
