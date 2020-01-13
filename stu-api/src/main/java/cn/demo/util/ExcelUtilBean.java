package cn.demo.util;

import java.util.List;

/**
 * @author shangfeng
 * @Title: ExcelUtilBean
 * @Package com.fh.excelutil
 * @Description: ${todo}
 * @date 2019/7/16  9:57
 */
public class ExcelUtilBean {
    //标题
    private String title;
    //列头
    private String[] columns;

    //数据
    private List<?> data;
    //参与导出的字段
    //['name','sex','birthday','address']
    //getName ,getSex,getBirthday
    private String[]  fields;

    //导出的sheet页的名称
    private String sheetName;
    //要保存文件夹名称
    private String mkdir;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getMkdir() {
        return mkdir;
    }

    public void setMkdir(String mkdir) {
        this.mkdir = mkdir;
    }
}
