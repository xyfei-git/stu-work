package cn.demo.util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
@Documented
public @interface PoiExcelAnnotation {
    String value() default "";
    String title() default "";
    String sheetName() default "";
    String mkdir() default "";
}
