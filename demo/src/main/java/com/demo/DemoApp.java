package com.demo;

public class DemoApp {
    public static void main(String[] args) {
        double a=999999.99999999,b=8888888888888.88888888888888,c=4444444444444444.444444444;
        while (true){
            double d = a * b * c;
            try {
                System.out.println("d = " + d);
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                System.out.println("有异常：" + e.toString());
            }
        }
    }
}
