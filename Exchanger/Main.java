package Exchanger;

import java.util.concurrent.Exchanger;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        final Exchanger<Integer> exchanger = new Exchanger<>();

        // Thread 1
        new Thread(new Runnable() {
            private Integer integer = 1;

            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        integer = (exchanger.exchange(integer));
                        System.out.println("Thread A = " + integer);
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        // Thread 2
        new Thread(new Runnable() {
            private Integer integer = 2;

            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        integer = (exchanger.exchange(integer));
                        System.out.println("Thread B = " + integer);
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(5000);
        System.exit(1);
    }

}