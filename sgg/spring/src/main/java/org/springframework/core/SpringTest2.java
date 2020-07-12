package org.springframework.core;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class SpringTest2 extends ArrayList<Integer> {
    Integer i1 = 10;
    boolean b1 = false;
    List<Integer> l1 = new ArrayList<Integer>();

    public static void main(String[] args) throws Exception{
        System.out.println(Boolean.class == boolean.class);
        Class c1 = Boolean.class;
        Class c2 = boolean.class;

        //判断是否为原始类型
        System.out.println(c1.isPrimitive());
        System.out.println(c2.isPrimitive());

        //如果是数组类型的class，则返回数组的类型
        System.out.println(char.class.getComponentType());
        System.out.println(char[].class.getComponentType());
        System.out.println(Character.class.getComponentType());
        System.out.println(Character[].class.getComponentType());

        System.out.println(Integer[].class == Array.newInstance(Integer[].class.getComponentType(),0).getClass());

        Method method = SpringTest2.class.getDeclaredMethod("m1", List.class, Long.class);
        Class<?> returnType = method.getReturnType();
        System.out.println(returnType);
        Class<?> parameter0 = method.getParameterTypes()[0];
        System.out.println(parameter0);

        System.out.println(method.getParameterTypes()[1]);

        System.out.println(method.getGenericReturnType());

        System.out.println("method.getGenericReturnType() instanceof ParameterizedType:"+(method.getGenericReturnType() instanceof ParameterizedType));

        System.out.println("method.getGenericReturnType().getClass():"+(method.getGenericReturnType().getClass()));

        System.out.println("-----"+(((ParameterizedType)method.getGenericReturnType()).getActualTypeArguments()[0] == Integer.class));

        System.out.println(method.getParameters()[0].isNamePresent());

        System.out.println((method.getParameters()[0]).getName());

        //会在编译后生成桥接方法
        System.out.println(SpringTest2.class.getMethod("add", Object.class).isBridge());

        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] names = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        System.out.println(names[0]);

        System.out.println("11111"+(((ParameterizedType)method.getGenericReturnType()).getActualTypeArguments()[0].getClass()));

        System.out.println(method.getGenericParameterTypes()[0]);

        Field f1 = SpringTest2.class.getDeclaredField("i1");

        System.out.println("SerializableTypeWrapper.forField"+SerializableTypeWrapper.forField(f1));

        Field f2 = SpringTest2.class.getDeclaredField("l1");

        System.out.println("SerializableTypeWrapper.forField l1"+SerializableTypeWrapper.forField(f2));

        Class p = ParameterizedType.class;

        System.out.println(p.isAssignableFrom(f2.getGenericType().getClass()));

        System.out.println(f2.getGenericType().getClass());

        System.out.println(((ParameterizedType)f2.getGenericType()).getRawType());

        System.out.println(f1.getGenericType() instanceof Class);
    }

    public List<Integer> m1(List<String> b, Long l){
        return null;
    }

    @Override
    public boolean add(Integer integer) {
        return super.add(integer);
    }
}
