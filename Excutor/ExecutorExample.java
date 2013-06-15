package Excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorExample {
    public static void main(String[] args) throws InterruptedException {

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable 1 - " + Thread.currentThread());
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable 2 - " + Thread.currentThread());
            }
        };

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable 3 - " + Thread.currentThread());
            }

        };

        final long TWO_SECONDS = 2_000;
        final long TEN_SECONDS = 10_000;
        final long SIXTY_ONE_SECONDS = 61_000;

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(runnable1);
        executorService.execute(runnable2);
        executorService.execute(runnable3);

        Thread.sleep(TWO_SECONDS);
        System.out.println("\nSlept for ~" + TWO_SECONDS / 1000 + " seconds\n");

        executorService.execute(runnable3);
        executorService.execute(runnable1);
        executorService.execute(runnable2);

        Thread.sleep(TEN_SECONDS);
        System.out.println("\nSlept for ~" + TEN_SECONDS / 1000 + " seconds\n");

        executorService.execute(runnable1);

        Thread.sleep(SIXTY_ONE_SECONDS);
        System.out.println("\nSlept for ~" + SIXTY_ONE_SECONDS / 1000 + " seconds\n");

        executorService.execute(runnable1);

        executorService.shutdown();
        System.out.println("\nExecutor shutdown? = " + executorService.isShutdown());
    }
}
