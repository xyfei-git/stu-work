package cn.demo.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExcelUtil {

    /**
     * 导出 Excel 的方法
     * @param list
     * @param response
     */
    public static void exportExcel(List list, HttpServletResponse response) throws NoSuchFieldException, IllegalAccessException {
            //获取要导出的数据
        ExcelBean excelData = getExcelData(list, list.get(0).getClass());
        //创建book
        XSSFWorkbook book=createWorkBook(excelData);
        uploadExcelWithSteam(book,response);
    }

    public static void uploadExcelWithSteam(XSSFWorkbook xssfWorkbook, HttpServletResponse response){
//设置contentType为vnd.ms-excel
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        // 对文件名进行处理。防止文件名乱码，这里前台直接定义了模板文件名，所以就不再次定义了
        //String fileName = CharEncodingEdit.processFileName(request, "stuTemplateExcel.xlsx");
        // Content-disposition属性设置成以附件方式进行下载
        response.setHeader("Content-disposition", "attachment;filename="+ UUID.randomUUID().toString()+".xlsx");
        //调取response对象中的OutputStream对象
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            xssfWorkbook.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建 workbook
     * @param excelData
     * @return XSSFWorkbook
     */
    private static XSSFWorkbook createWorkBook(ExcelBean excelData) throws NoSuchFieldException, IllegalAccessException {
        //创建 workbook
        XSSFWorkbook book = new XSSFWorkbook();
        //创建 sheet 页
        XSSFSheet sheet = book.createSheet(excelData.getSheetName());
        //创建 sheet 页 第一行    标题行
        XSSFRow headRow = sheet.createRow(0);
        //创建 标题单元格
        XSSFCell headCell =headRow.createCell(0);
        //给单元格赋值
        headCell.setCellValue(excelData.getTitle());
        //声明 合并单元格
        CellRangeAddress cellRangeAddress=new CellRangeAddress(0,0,0,excelData.getColoums().size()-1);
        //添加 合并单元格单元格
        sheet.addMergedRegion(cellRangeAddress);

        //创建 标题行
        XSSFRow colunmRow = sheet.createRow( 1);
        List<String> coloums = excelData.getColoums();

        for (int i = 0; i < coloums.size(); i++) {
            XSSFCell titleCell = colunmRow.createCell(i);
            titleCell.setCellValue(coloums.get(i));
        }
        List<String> flieds = excelData.getFlieds();
        List data = excelData.getData();
        //创建 数据行
        for (int i = 0; i < data.size(); i++) {
            XSSFRow dataRow = sheet.createRow(i + 2);
            Object o = data.get(i);
            //创建数据列
            for (int j = 0; j < flieds.size(); j++) {
                String s = flieds.get(j);
                XSSFCell dataCell = dataRow.createCell(j);
                Class<?> aClass = o.getClass();
                Field declaredField = aClass.getDeclaredField(flieds.get(j));
                declaredField.setAccessible(true);
                Object dataValue = declaredField.get(o);
                //判断数据类型
                if (dataValue instanceof Date){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    Date d=(Date)dataValue;
                    String format = sdf.format(d);
                    dataCell.setCellValue(format);
                }else if (dataValue instanceof String){
                    dataCell.setCellValue((String) dataValue);
                }else if (dataValue instanceof Integer){
                    dataCell.setCellValue((Integer)dataValue);
                }
            }
        }
        return book;
    }

    /**
     * 获取要导出的 数据
     * @param clazz
     * @return
     */
    public static ExcelBean getExcelData(List list,Class clazz){
        ExcelBean util=new ExcelBean();
        //获取类上的注解
        ExcelAnnotation annotation = (ExcelAnnotation) clazz.getAnnotation(ExcelAnnotation.class);
        if (annotation!=null){
            //获取注解中 sheet 名
            util.setSheetName(annotation.sheetName());
            //获取注解中 标题名
            util.setTitle(annotation.title());
        }
        //获取 全部属性
        Field[] declaredFields = clazz.getDeclaredFields();
        List<String> collomnList=new ArrayList<>();
        List<String> fields=new ArrayList<>();
        //判断属性是否有添加注解，为导出字段
        for (int i = 0; i < declaredFields.length; i++) {
            ExcelAnnotation annotation1 = declaredFields[i].getAnnotation(ExcelAnnotation.class);
            if (annotation1!=null){
                String colomn = annotation1.colomn();
                if (StringUtils.isNotBlank(colomn)){
                    collomnList.add(colomn);
                    fields.add(declaredFields[i].getName());
                }
            }
        }
        util.setColoums(collomnList);
        util.setData(list);
        util.setFlieds(fields);
        return util;
    }





}
