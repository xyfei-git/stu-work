package cn.demo.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//次注解可加在类上 属性上
@Target({ElementType.FIELD,ElementType.TYPE})
//该注解程序运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAnnotation {
    String title() default  "";

    String colomn() default "";

    String sheetName() default "";

}
