package cn.demo.entity.po;

import cn.demo.util.ExcelAnnotation;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_city")
@ExcelAnnotation(sheetName = "城市表",title = "城市数据")
public class City {
    @ExcelAnnotation(colomn = "主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField
    @ExcelAnnotation(colomn = "名称")
    private String name;

    @TableField
    private Integer pid;

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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
