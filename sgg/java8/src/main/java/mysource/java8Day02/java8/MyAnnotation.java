package mysource.java8Day02.java8;

import java.lang.annotation.*;



/**
 * ElementType.TYPE_PARAMETER 类型注解
 * @Repeatable(MyAnnotations.class) 可重复注解
 */

@Repeatable(MyAnnotations.class)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER,ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String value() default "hello";
}
