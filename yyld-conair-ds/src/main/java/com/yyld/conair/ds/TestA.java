package com.yyld.conair.ds;

public class TestA {
    public static void main(String[] args) {
        Targetable targetable = new Adapter();
        targetable.method1();
        targetable.method2();
    }
}

class Source {
    public void method1() {
        System.out.println("This is original method...");
    }
}

interface Targetable {

    /**
     * 与原类中的方法相同
     */
    public void method1();

    /**
     * 新类的方法
     */
    public void method2();
}

class Adapter extends Source implements Targetable {

    @Override
    public void method2() {
        System.out.println("This is the targetable method...");
    }
}


