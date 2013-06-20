package forkjoinbenchmark;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

public class ForkJoinBenchmark {

    static private Customer[] customers;

    public static void main(String[] args) {
        long parallel = 0;
        long sequential = 0;
        final int loops = 25;
        setUp();
        for (int j = 0; j < loops; j++) {
            sequential += computeSequential().getCalculationTime();
            parallel += computeParallel().getCalculationTime();
        }
        tearDown(sequential, parallel, loops);
    }

    private static void tearDown(final long sequential, final long parallel, final int loops) {
        // print computing result
        final StatisticResult sequentialResult = computeSequential();
        final StatisticResult parallelResult = computeParallel();
        assert (sequentialResult.getHighestCustomer().getName().equals(parallelResult.getHighestCustomer().getName()));
        assert (sequentialResult.getTotalSale().equals(parallelResult.getTotalSale()));
        System.out.println("Result of the last sequential computation run:");
        System.out.println("----------------------------------------------");
        printResult(sequentialResult);
        System.out.println("Result of the last parallel computation run:");
        System.out.println("--------------------------------------------");
        printResult(parallelResult);
        final double percentage = ((double) (sequential / loops) / (double) (parallel / loops));
        System.out.println("benchmark done!\n");
        System.out.println("Average Parallel: " + parallel / loops);
        System.out.println("Average Sequential: " + sequential / loops);
        System.out.println("Fork/Join is about " +
                (NumberFormat.getPercentInstance().format(percentage) + " faster! "));
    }

    private static void setUp() {
        System.out.println("Initializing test data...");
        customers = new Customer[1_000_000];
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer("Customer #" + (i + 1), new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 10_000)));
        }
        System.out.println("Initializing done!\n");
        System.out.println("Warming up JVM...");
        // warm up JVM
        for (int i = 0; i < 10; i++) {
            computeSequential();
            computeParallel();
        }
        System.out.println("Warming up JVM done!\n");
        System.out.println("Running benchmark...\n");
    }

    private static StatisticResult computeParallel() {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        final StatisticTask statisticTask = new StatisticTask(customers, 0, customers.length);
        final long start = System.currentTimeMillis();
        final StatisticResult result = forkJoinPool.invoke(statisticTask);
        final long end = System.currentTimeMillis() - start;
        return new StatisticResult(result.getHighestCustomer(), result.getTotalSale(), end);
    }

    private static StatisticResult computeSequential() {
        final StatisticResult statisticResult;
        final long start = System.currentTimeMillis();
        statisticResult = Algorithm.computeResult(customers, 0, customers.length);
        final long end = System.currentTimeMillis() - start;
        return new StatisticResult(statisticResult.getHighestCustomer(), statisticResult.getTotalSale(), end);
    }

    private static void printResult(StatisticResult result) {
        System.out.println("Computation finished after " + result.getCalculationTime() + " ms");
        System.out.println("Total sales = " + getFormattedAmount(result.getTotalSale()));
        System.out.println("Average sales per customer = " + getFormattedAmount(result.getTotalSale().divide(new BigDecimal(customers.length))));
        System.out.println("Customer with highest sale = " + result.getHighestCustomer().getName() + "\n");
    }

    private static String getFormattedAmount(BigDecimal amount) {
        return NumberFormat.getCurrencyInstance().format(amount);
    }
}
