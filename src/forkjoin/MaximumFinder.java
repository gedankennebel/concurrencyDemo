package forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class MaximumFinder extends RecursiveTask<Integer> {
    // Grenze um sofort zu bearbeiten
    private static final int THRESHOLD = 10;

    private final int[] data;
    private final int start, end;

    public MaximumFinder(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start < THRESHOLD) {
            return computeDirectly();
        } else {
            final int split = (end - start) / 2;
            final MaximumFinder left =
                    new MaximumFinder(data, start, start + split - 1);
            left.fork();
            final MaximumFinder right =
                    new MaximumFinder(data, start + split, end);
            return Math.max(right.compute(), left.join());
        }
    }

    private Integer computeDirectly() {
        System.out.println("Start: " + start + " Ende: " + end);
        int max = Integer.MIN_VALUE;
        for (int i = start; i < end; i++) {
            if (data[i] > max)
                max = data[i];
        }
        return max;
    }

    public static void main(String[] args) {
        // Erstelle Test-Array mit Random-Zahlen
        final int[] data = new int[100];
        final Random random = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(1, 1_000);
        }
//        // Erstelle ForkJoin-Pool und stoße Algorithmus an
        final ForkJoinPool pool = new ForkJoinPool();
        final MaximumFinder finder = new MaximumFinder(data, 0, data.length);
        System.out.println(pool.invoke(finder));
    }
}
