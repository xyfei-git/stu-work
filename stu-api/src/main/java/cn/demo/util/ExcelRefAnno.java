package cn.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shangfeng
 * @Title: ExcelRefAnno
 * @Package com.fh.excelutil
 * @Description: ${todo}
 * @date 2019/7/17  15:26
 */
public class ExcelRefAnno {


    /**
     * 根据注解获取要导出的字段和名称
     * @param bean
     * @param clazz
     */
    public static void getExportField(ExcelUtilBean bean,Class clazz,Map<String,Object> m) {
        List<String> fieldArr=new ArrayList<String>();
        List<String> columnArr=new ArrayList<String>();
        Field[] fields=clazz.getDeclaredFields();
        List<String> filedes =null;
        List<String> coloums=null;
        if (m!=null){
            filedes = (List<String>) m.get("fileds");
            coloums = (List<String>) m.get("coloums");
        }
        for (int i = 0; i <fields.length ; i++) {
            PoiExcelAnnotation annotation = fields[i].getAnnotation(PoiExcelAnnotation.class);
            if(annotation != null){
                String name = fields[i].getName();
                for (int j = 0; j <filedes.size(); j++) {
                    if (filedes.get(j).equals(name)){
                        fieldArr.add(name);
                        columnArr.add(annotation.value());
                    }
                }
            }
        }
        bean.setColumns( columnArr.toArray(new String[columnArr.size()]));
        bean.setFields( fieldArr.toArray(new String[fieldArr.size()]));

    }

    /**
     *导出Excel的公共调用方法
     * @param clazz
     * @return
     */
    public static  String exportExcel(List<?> dataList,Class clazz,Map<String,Object> m) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        ExcelUtilBean bean=new ExcelUtilBean();
        getExportField(bean,clazz,m);
        PoiExcelAnnotation annotation = (PoiExcelAnnotation) clazz.getAnnotation(PoiExcelAnnotation.class);
        bean.setTitle(annotation.title());
        bean.setSheetName(annotation.sheetName());
        bean.setMkdir(annotation.mkdir());
        bean.setData(dataList);
        return ExportExcelUtil.exportExcel(bean,clazz);
    }

}
