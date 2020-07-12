package com.atguigu.visitor;

import java.util.Iterator;

public abstract class Entry implements Element{
     public abstract String getName();
     public abstract int getSize();
     public abstract void printList(String prefix);
     public  void printList(){
             printList("");
          }
     public  Entry add(Entry entry) throws RuntimeException{
             throw new RuntimeException();
          }
     public Iterator iterator() throws RuntimeException{
              throw new RuntimeException();
          }
     public  String toString(){
               return getName()+"<"+getSize()+">";
           }
 }