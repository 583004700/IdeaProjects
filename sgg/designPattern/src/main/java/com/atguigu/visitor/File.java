package com.atguigu.visitor;

public class File extends Entry {

    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void printList(String prefix) {
        System.out.println(prefix + "/" + this);
    }

    public void accept(Visitor visitor) {
        //  System.out.println("开始访问文件:"+this);
        visitor.visit(this);
        // System.out.println("结束访问文件:"+this);
        // System.out.println();
    }

}
