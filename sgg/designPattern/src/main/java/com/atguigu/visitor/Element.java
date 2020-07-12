package com.atguigu.visitor;

public interface Element {
    public abstract void accept(Visitor visitor);
}
