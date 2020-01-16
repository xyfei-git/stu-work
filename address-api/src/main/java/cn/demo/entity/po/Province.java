package cn.demo.entity.po;

import cn.demo.util.ExcelAnnotation;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@ExcelAnnotation(title = "城市表",sheetName = "城市")
@TableName("t_province")
public class Province {
    @ExcelAnnotation(colomn = "主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField
    @ExcelAnnotation(colomn = "名称")
    private String name;
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
}
