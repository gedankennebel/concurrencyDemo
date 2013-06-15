package sum;

import java.util.concurrent.Callable;

class SummarizeCallable implements Callable<Long> {
    private final int from;
    private final int to;
    private long[] array;

    SummarizeCallable(long[] array, int from, int to) {
        this.from = from;
        this.to = to;
        this.array = array;
    }

    @Override
    public Long call() throws Exception {
        long count = 0L;
        // Calculating sum of the given array range
        for (int i = from; i < to; i++) {
            count += array[i];
        }
        return count;
    }
}
