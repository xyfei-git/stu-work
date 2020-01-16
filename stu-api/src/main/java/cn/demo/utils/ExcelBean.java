package cn.demo.utils;

import java.util.List;

public class ExcelBean {

    private String sheetName;

    private String title;

    private List<String> coloums;

    private List<String> flieds;

    private List<?> data;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public List<String> getFlieds() {
        return flieds;
    }

    public void setFlieds(List<String> flieds) {
        this.flieds = flieds;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getColoums() {
        return coloums;
    }

    public void setColoums(List<String> coloums) {
        this.coloums = coloums;
    }
}
