package sum;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ExecutorSum {

    private void fillArray(long[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorSum sum = new ExecutorSum();
        long[] array = new long[100_000_000];
        sum.fillArray(array);

        for (int i = 0; i < 10; i++) {
            int processors = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(processors);
            List<Future<Long>> results;
            final long start = System.currentTimeMillis();

            // array size/No. of processors
            int splitCount = array.length / processors;

            //Split pool size into even size for maximum through put
            results = executor.invokeAll(asList(new SummarizeCallable(array, 0,
                    splitCount), new SummarizeCallable(array, splitCount + 1,
                    splitCount * 2), new SummarizeCallable(array, (splitCount * 2) + 1,
                    splitCount * 3), new SummarizeCallable(array, (splitCount * 3) + 1,
                    array.length)
            ));
            executor.shutdown();
            // Calculating final result sum
            long count = 0;
            for (Future<Long> result : results) {
                count += result.get();
            }
            System.out.println("Ergebnis: " + count);
            System.out.println("Sequenzielle Berechnungszeit: "
                    + (System.currentTimeMillis() - start) + " ms");
        }

        System.out.println("\nStarte Fork/Join");
        System.out.println("Number of processors available: " + Runtime.getRuntime().availableProcessors());
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        for (int i = 0; i < 10; i++) {
            RecursiveSumTask task = new RecursiveSumTask(array, 0, array.length);
            final long start = System.currentTimeMillis();
            System.out.println("Result: " + forkJoinPool.invoke(task));
            System.out.println("Parallel processing time: " + (System.currentTimeMillis() - start) + " ms");
        }
        System.out.println("Number of steals: " + forkJoinPool.getStealCount() + "\n");
    }
}

