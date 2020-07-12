package com.atguigu.visitor;

import java.util.Iterator;

/**
 * 访问者去操作被访问者的元素节点
 */
public class Client {
    public static void main(String[] args) {
        Directory root = new Directory("根目录");

        Directory life = new Directory("我的生活");
        File eat = new File("吃火锅.txt", 100);
        File sleep = new File("睡觉.html", 100);
        File study = new File("学习.txt", 100);
        life.add(eat);
        life.add(sleep);
        life.add(study);

        Directory work = new Directory("我的工作");
        File write = new File("写博客.doc", 200);
        File paper = new File("写论文.html", 200);
        File homework = new File("写家庭作业.docx", 200);
        work.add(write);
        work.add(paper);
        work.add(homework);

        Directory relax = new Directory("我的休闲");
        File music = new File("听听音乐.js", 200);
        File walk = new File("出去转转.psd", 200);
        relax.add(music);
        relax.add(walk);

        Directory read = new Directory("我的阅读");
        File book = new File("学习书籍.psd", 200);
        File novel = new File("娱乐小说.txt", 200);
        read.add(book);
        read.add(novel);

        root.add(life);
        root.add(work);
        root.add(relax);
        root.add(read);

        root.accept(new ListVisitor());
        System.out.println("========================");
        FileVisitor visitor = new FileVisitor(".psd");
        root.accept(visitor);
        Iterator it = visitor.getFiles();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
