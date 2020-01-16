import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Tests {

    public static void main(String [] args){
        User u=new User();
        Stu s=new Stu();
        query(u);
        query(s);
    }
    public static void query(Object obj){
        Class<?> aClass = obj.getClass();
        String sql="select ";
        Field[] declaredFields = aClass.getDeclaredFields();
        List lisy=new ArrayList();
        String main="";
        for (int i = 0; i < declaredFields.length; i++) {
            Anno annotation = declaredFields[i].getAnnotation(Anno.class);
            if (annotation!=null){
                if (StringUtils.isNotBlank(annotation.column())){
                    lisy.add(annotation.column());
                }
                if (StringUtils.isNotBlank(annotation.main())){
                    main = annotation.main();
                }
            }
        }
        if (StringUtils.isNotBlank(main)){
            sql+=main+" ";
        }
        for (int i = 0; i <lisy.size(); i++) {
            if (StringUtils.isNotBlank(main)){
                if (i==0){
                    sql+=", "+lisy.get(i);
                }else if (i==lisy.size()+1){
                    sql+=lisy.get(i);
                }else{
                    sql+=" ,"+lisy.get(i)+" ";
                }
            }else{
                if (i==0){
                    sql+=lisy.get(i);
                }else if (i==lisy.size()+1){
                    sql+=" "+lisy.get(i);
                }else{
                    sql+=" , "+lisy.get(i);
                }
            }

        }
        Anno annotation = aClass.getAnnotation(Anno.class);
        if (annotation!=null){
            if (StringUtils.isNotBlank(annotation.table())){
                sql+=" from "+annotation.table();
            }
        }
        System.out.println(sql);
    }

}
