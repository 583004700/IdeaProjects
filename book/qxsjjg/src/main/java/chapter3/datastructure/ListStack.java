package chapter3.datastructure;


import java.lang.reflect.Array;

public class ListStack<T> {

    private T[] arr;
    private int top;
    private int base;
    private int maxSize;

    public ListStack(Class<T> c,int maxSize) {
        this.maxSize = maxSize;
        top = 0;
        arr = (T[])Array.newInstance(c,maxSize);
    }

    public boolean push(T ele){
        if(top-base==maxSize){
            return false;
        }
        arr[top++] = ele;
        return true;
    }

    public T pop(){
        if(top==base){
            return null;
        }
        return arr[--top];
    }

    public T getTop(){
        if(top==base){
            return null;
        }
        return arr[top-1];
    }

}
