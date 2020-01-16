package cn.demo.entity;
import cn.demo.util.PoiExcelAnnotation;
import cn.demo.utils.ExcelAnnotation;

import java.util.Date;
@PoiExcelAnnotation(title = "学生表数据",sheetName="学生",mkdir="/excel/")
@ExcelAnnotation(title = "学生表数据",sheetName = "学生信息")
public class ExcelStu {
    @PoiExcelAnnotation("主键")
    @ExcelAnnotation(colomn = "主键")
    private Integer id;
    @PoiExcelAnnotation("姓名")
    @ExcelAnnotation(colomn = "姓名")
    private String name;
    @PoiExcelAnnotation("年龄")
    @ExcelAnnotation(colomn = "年龄")
    private Integer age;
    @PoiExcelAnnotation("性别")
    @ExcelAnnotation(colomn = "性别")
    private Integer sex;
    @PoiExcelAnnotation("生日")
    @ExcelAnnotation(colomn = "生日")
    private Date birthday;
    @PoiExcelAnnotation("地址")
    @ExcelAnnotation(colomn = "地址")
    private String address;
    @PoiExcelAnnotation("图片")
    @ExcelAnnotation(colomn = "图片")
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
