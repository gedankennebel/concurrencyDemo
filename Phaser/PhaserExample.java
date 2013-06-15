package Phaser;

import java.util.concurrent.Phaser;

public class PhaserExample {
    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(2) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("YAHOOO");
                return getRegisteredParties() == 3;
            }
        };

        System.out.println(phaser.getUnarrivedParties());
        System.out.println(phaser.getPhase());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                phaser.arrive();
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(phaser.getPhase());

        phaser.bulkRegister(1);

        Thread thread3 = new Thread(runnable);
        Thread thread4 = new Thread(runnable);
        Thread thread5 = new Thread(runnable);
        thread5.start();
        thread3.start();

        Thread.sleep(2000);
        thread4.start();

        thread4.join();

        System.out.println(phaser.getPhase());

        System.out.println(phaser.isTerminated());

    }
}
