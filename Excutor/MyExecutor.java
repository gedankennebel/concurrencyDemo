package Excutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyExecutor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Long> future = executor.submit(new MyCallable());
        future.get();
    }

    private static class MyCallable implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            long sum = 0;
            for (long i = 0; i <= 100; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
