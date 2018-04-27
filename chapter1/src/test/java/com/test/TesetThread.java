package com.test;

/**
 * Created by HP on 2014-04-10.
 */
public class TesetThread {

    public synchronized void a1(){
        while (true){
            System.out.println("a1");
        }
    }

    public synchronized void a2(){
        while (true){
            System.out.println("a2");
        }
    }

    public static void main(String[] args) {
        TesetThread t1 = new TesetThread();
        TesetThread t2 = new TesetThread();

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                t1.a1();
            }
        });
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                t2.a2();
            }
        });
    }
}
