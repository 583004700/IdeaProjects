package com.atguigu.factory.simplefactory.pizzastore.order;

public class PizzaStore {
    public static void main(String[] args) {
        SimpleFactory simpleFactory = new SimpleFactory();
        new OrderPizza(simpleFactory);
    }
}
