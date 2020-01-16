package cn.demo.entity.po;

import cn.demo.util.ExcelAnnotation;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_area")
@ExcelAnnotation(sheetName = "县级",title = "县级列表")
public class Area {
    @ExcelAnnotation(colomn = "主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField
    @ExcelAnnotation(colomn = "县")
    private String name;
    @TableField
    private Integer cid;

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

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}
