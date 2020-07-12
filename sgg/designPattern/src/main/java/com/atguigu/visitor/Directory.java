package com.atguigu.visitor;

import java.util.ArrayList;
import java.util.Iterator;

public class Directory extends Entry {

    String name;
    ArrayList entrys = new ArrayList();

    public Directory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        int size = 0;
        Iterator it = entrys.iterator();
        while (it.hasNext()) {
            size += ((Entry) it.next()).getSize();
        }
        return size;
    }

    public Entry add(Entry entry) {
        entrys.add(entry);
        return this;
    }

    public Iterator iterator() {
        return entrys.iterator();
    }

    public void printList(String prefix) {
        System.out.println(prefix + "/" + this);
        Iterator it = entrys.iterator();
        Entry entry;
        while (it.hasNext()) {
            entry = (Entry) it.next();
            entry.printList(prefix + "/" + name);
        }
    }

    public void accept(Visitor visitor) {
        //  System.out.println("开始访问文件夹:"+this);
        visitor.visit(this);
        //   System.out.println("结束访问文件夹:"+this);
    }

}
