package mysource.java8Day02.java8;

import org.junit.Test;

import java.lang.reflect.Method;

public class TestAnnotation {

    @Test
    public void test1() throws Exception{
        Class<TestAnnotation> clazz = TestAnnotation.class;
        Method m1 = clazz.getMethod("show");
        MyAnnotation[] mas = m1.getAnnotationsByType(MyAnnotation.class);
        for(MyAnnotation myAnnotation:mas){
            System.out.println(myAnnotation.value());
        }
    }


    @MyAnnotation("HELLO")
    @MyAnnotation("WORLD")
    public void show(@MyAnnotation("abc") String str){

    }
}
