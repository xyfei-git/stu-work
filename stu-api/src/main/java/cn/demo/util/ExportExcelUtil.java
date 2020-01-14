package cn.demo.util;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author shangfeng
 * @Title: ExportExcelUtil
 * @Package com.fh.excelutil
 * @Description: ${todo}
 * @date 2019/7/16  10:04
 */
public class ExportExcelUtil {

    /**
     * 导出Exce的工具方法
     * @param bean
     * @param clazz
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static  String exportExcelforLocal(ExcelUtilBean bean,Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

                //声明工作薄
        XSSFWorkbook workbook=new XSSFWorkbook();

        //获取样式
        XSSFCellStyle tilteStyle=PoiCellStyle.titleStyle(workbook);
        XSSFCellStyle colunmStyle=PoiCellStyle.colunmStyle(workbook);

        //创建sheet页
        XSSFSheet sheet=workbook.createSheet(bean.getSheetName());
        //创建标题行
        XSSFRow titleRow=sheet.createRow(0);
        XSSFCell tileCell=titleRow.createCell(0);
        tileCell.setCellValue(bean.getTitle());
        tileCell.setCellStyle(tilteStyle);
        //第一行需要合并单元格
        //CellRangeAddress(startRow,endRow,startCell,endCell)
        CellRangeAddress cellRangeAddress=new CellRangeAddress(0,0,0,bean.getColumns().length-1);
            //才是去合并单元格
        sheet.addMergedRegion(cellRangeAddress);

        //创建列头
        XSSFRow columnRow=sheet.createRow(1);
        columnRow.setHeight((short)400);
        String[] colums=bean.getColumns();
        for(int i=0;i<colums.length;i++){
            XSSFCell cellCol=columnRow.createCell(i);
            cellCol.setCellValue(colums[i]);
            cellCol.setCellStyle(colunmStyle);
        }

        //创建数据行
        List<Object> list= (List<Object>) bean.getData();
        for(int i=0;i<list.size();i++){
            Object dataObj=list.get(i);
            XSSFRow dataRow=sheet.createRow(i+2);
            dataRow.setHeight((short)400);

            //java的反射机制的使用.
            //给我一个类CLSSS，我可以获取里边的方法，属性。。。。：给我一个对象我可以操作里边的额方法和属性
                String[] fields=bean.getFields();
                for (int j=0;j<fields.length;j++){
                    String field=fields[j];
                    String methodName=getMethodName(field);
                    //获取类中的方法
                    Method method=clazz.getMethod(methodName);
                    //该方法在对象中执行
                    Object value=method.invoke(dataObj);
                    //创建单元格
                    XSSFCell dataCell=dataRow.createCell(j);
                    XSSFCellStyle dataStyle=PoiCellStyle.dataStyle(workbook);
                    //如何判断返回值类型
                    if(value instanceof Date){
                        //转成String
                        //就用excel自带的日期格式
                        XSSFDataFormat dataFormat=workbook.createDataFormat();
                        dataStyle.setDataFormat( dataFormat.getFormat("yyyy/MM/dd"));
                        dataCell.setCellValue((Date) value);
                        dataCell.setCellStyle(dataStyle);
                    }else if(value instanceof Integer){
                        dataCell.setCellValue((Integer)value);
                    }else if(value instanceof Double){
                        dataCell.setCellValue((Double)value);
                    }else if(value instanceof Boolean){
                        dataCell.setCellValue((Boolean)value);
                    }else if(value instanceof Float){
                        dataCell.setCellValue((Float)value);
                    }else if(value instanceof Long){
                        dataCell.setCellValue((Long)value);
                    }else{
                        dataCell.setCellValue((String)value);
                    }
                    dataCell.setCellStyle(dataStyle);
                }
        }

        for(int i=0;i<colums.length;i++){
            sheet.autoSizeColumn((short) i);
            // 解决自动设置列宽中文失效的问题
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
        }
        //上传到ftp
        return uploadExcelToFtp(workbook,bean);
    }

    /**
     * 以流的形式返回到浏览器
     * @param bean
     * @param clazz
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static  void exportExcelforSteam(ExcelUtilBean bean,Class clazz,HttpServletResponse response) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //声明工作薄
        XSSFWorkbook workbook=new XSSFWorkbook();

        //获取样式
        XSSFCellStyle tilteStyle=PoiCellStyle.titleStyle(workbook);
        XSSFCellStyle colunmStyle=PoiCellStyle.colunmStyle(workbook);

        //创建sheet页
        XSSFSheet sheet=workbook.createSheet(bean.getSheetName());
        //创建标题行
        XSSFRow titleRow=sheet.createRow(0);
        XSSFCell tileCell=titleRow.createCell(0);
        tileCell.setCellValue(bean.getTitle());
        tileCell.setCellStyle(tilteStyle);
        //第一行需要合并单元格
        //CellRangeAddress(startRow,endRow,startCell,endCell)
        CellRangeAddress cellRangeAddress=new CellRangeAddress(0,0,0,bean.getColumns().length-1);
        //才是去合并单元格
        sheet.addMergedRegion(cellRangeAddress);

        //创建列头
        XSSFRow columnRow=sheet.createRow(1);
        columnRow.setHeight((short)400);
        String[] colums=bean.getColumns();
        for(int i=0;i<colums.length;i++){
            XSSFCell cellCol=columnRow.createCell(i);
            cellCol.setCellValue(colums[i]);
            cellCol.setCellStyle(colunmStyle);
        }

        //创建数据行
        List<Object> list= (List<Object>) bean.getData();
        for(int i=0;i<list.size();i++){
            Object dataObj=list.get(i);
            XSSFRow dataRow=sheet.createRow(i+2);
            dataRow.setHeight((short)400);

            //java的反射机制的使用.
            //给我一个类CLSSS，我可以获取里边的方法，属性。。。。：给我一个对象我可以操作里边的额方法和属性
            String[] fields=bean.getFields();
            for (int j=0;j<fields.length;j++){
                String field=fields[j];
                String methodName=getMethodName(field);
                //获取类中的方法
                Method method=clazz.getMethod(methodName);
                //该方法在对象中执行
                Object value=method.invoke(dataObj);
                //创建单元格
                XSSFCell dataCell=dataRow.createCell(j);
                XSSFCellStyle dataStyle=PoiCellStyle.dataStyle(workbook);
                //如何判断返回值类型
                if(value instanceof Date){
                    //转成String
                    //就用excel自带的日期格式
                    XSSFDataFormat dataFormat=workbook.createDataFormat();
                    dataStyle.setDataFormat( dataFormat.getFormat("yyyy/MM/dd"));
                    dataCell.setCellValue((Date) value);
                    dataCell.setCellStyle(dataStyle);
                }else if(value instanceof Integer){
                    dataCell.setCellValue((Integer)value);
                }else if(value instanceof Double){
                    dataCell.setCellValue((Double)value);
                }else if(value instanceof Boolean){
                    dataCell.setCellValue((Boolean)value);
                }else if(value instanceof Float){
                    dataCell.setCellValue((Float)value);
                }else if(value instanceof Long){
                    dataCell.setCellValue((Long)value);
                }else{
                    dataCell.setCellValue((String)value);
                }
                dataCell.setCellStyle(dataStyle);
            }
        }

        for(int i=0;i<colums.length;i++){
            sheet.autoSizeColumn((short) i);
            // 解决自动设置列宽中文失效的问题
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
        }
        //上传到ftp
        uploadExcelWithSteam(workbook,bean,response);
    }


    /**
     * 以流返回excel
     * @param xssfWorkbook
     * @param bean
     * @param response
     */
    public static void uploadExcelWithSteam(XSSFWorkbook xssfWorkbook, ExcelUtilBean bean, HttpServletResponse response){
//设置contentType为vnd.ms-excel
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        // 对文件名进行处理。防止文件名乱码，这里前台直接定义了模板文件名，所以就不再次定义了
        //String fileName = CharEncodingEdit.processFileName(request, "stuTemplateExcel.xlsx");
        // Content-disposition属性设置成以附件方式进行下载
        response.setHeader("Content-disposition", "attachment;filename="+UUID.randomUUID().toString()+".xlsx");
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
     * 上传到指定的文件家中
     * @param workbook
     * @param bean
     * @return
     */
    public static String uploadExcelToFtp(XSSFWorkbook workbook,ExcelUtilBean bean){
        //创建文件目录
        String realPath=createMkdir(bean);
        //获取新的文件名
        String newFileName=createFileName();
        //把一个workbook变成一个输入流
        ByteArrayOutputStream bos=null;
        // 输出流
        FileOutputStream fos = null;
        try {
            fos=new FileOutputStream(realPath + "/" + newFileName);
            bos=new ByteArrayOutputStream();
            workbook.write(bos);
            byte[] bytes=bos.toByteArray();
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean.getMkdir()+ "/" + newFileName;
    }

    /**
     * 生成新的文件名
     * @return
     */
    public static String createFileName(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid+".xlsx";
    }
    /**
     * 检查文件目录是否存在
     * 不存创建文件目录
     * @param bean
     */
    public static  String createMkdir(ExcelUtilBean bean){
        HttpServletRequest request=((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
        //获取项目在你电脑硬盘的绝对路径
        //d://tomcat/ssm/
        String realPath="D:\\myProject\\stu-work\\stu-web\\src\\main\\webapp";
        realPath= realPath+bean.getMkdir();

        //检查文件路径是否存在不存在进行创建
        File file=new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        return realPath;
    }
    /**
     * 拼接get和set方法
     * @param field
     * @return
     */
    private static String  getMethodName(String field){
        return "get"+field.substring(0,1).toUpperCase()+field.substring(1);
    }
}
