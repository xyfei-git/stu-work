package cn.demo.util;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author shangfeng
 * @Title: PoiCellStyle
 * @Package com.fh.excelutil
 * @Description: ${todo}
 * @date 2019/7/17  9:38
 */
public class PoiCellStyle {

    /**
     * 设置标题的样式
     * @param workbook
     * @return
     */
    public static XSSFCellStyle titleStyle(XSSFWorkbook workbook){
            //字体变大，加粗，居中，行高，
        XSSFFont font=workbook.createFont();
        //这只加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        //设置字体大小
        font.setFontHeightInPoints((short) 24);
        //  font.setFontName("黑体"); //字体
        font.setItalic(true); //是否使用斜体
        //        font.setStrikeout(true); //是否使用划线
        //font.setColor(HSSFFont.COLOR_RED); //字体颜色
        XSSFCellStyle cellStyle=workbook.createCellStyle();
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 列头样式
     * @param workbook
     * @return
     */
    public static XSSFCellStyle colunmStyle(XSSFWorkbook workbook){
        //字体变大，加粗，居中，行高，
        //创建字体对象
        XSSFFont font=workbook.createFont();
        //这只加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        //设置字体大小
        font.setFontHeightInPoints((short) 14);
        //创建单元格样式
        XSSFCellStyle cellStyle=workbook.createCellStyle();
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        cellStyle.setFont(font);
        //设置边框
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderTop((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);

        // 设置单元格的背景色
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);// 设置前景填充样式
        cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        return cellStyle;

    }

    public static  XSSFCellStyle dataStyle(XSSFWorkbook workbook){
        XSSFFont font=workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 12);
        //创建单元格样式
        XSSFCellStyle cellStyle=workbook.createCellStyle();
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        cellStyle.setFont(font);
        //设置边框
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderTop((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);
        return cellStyle;


    }
}
