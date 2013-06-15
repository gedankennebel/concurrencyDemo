package forkJoinDemo;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class IntersectionsFinder extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 100;

    private final StretchJava[] stretches;
    private final int start;
    private final int end;

    final AtomicInteger count = new AtomicInteger();

    public IntersectionsFinder(StretchJava[] stretches, int start, int end) {
        this.stretches = stretches;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            return computeDirectly();
        } else {
            final int split = (end - start) / 2;
            final IntersectionsFinder left =
                    new IntersectionsFinder(stretches, start, start + split - 1);
            left.fork();
            final IntersectionsFinder right =
                    new IntersectionsFinder(stretches, start + split, end);
            return right.compute() + left.join();
        }
    }

    private Integer computeDirectly() {
        for (int index1 = start; index1 < end; index1++) {
            int localCount = 0;
            for (int index2 = start + 1; index2 < stretches.length; index2++) {
                if (stretches[index1].intersects(stretches[index2])) {
                    localCount++;
                }
            }
            count.getAndAdd(localCount);
        }
        return count.get();
    }
}
