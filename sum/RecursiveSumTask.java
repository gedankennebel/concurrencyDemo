package sum;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

class RecursiveSumTask extends RecursiveTask<Long> {
    private final long from;
    private final long to;
    private long[] array;
    final int threshold = 1_00_0000;

    RecursiveSumTask(long[] array, long from, long to) {
        this.from = from;
        this.to = to;
        this.array = array;
    }

    @Override
    protected Long compute() {
        long count = 0L;
        List<RecursiveTask<Long>> forks = new LinkedList<>();
        if (to - from > threshold) {
            // task is huge so divide in half
            long mid = (from + to) >>> 1;

            //Divided the given task into task1 and task2
            RecursiveSumTask task1 = new RecursiveSumTask(array, from, mid);
            forks.add(task1);
            task1.fork();

            RecursiveSumTask task2 = new RecursiveSumTask(array, mid, to);
            forks.add(task2);
            task2.fork();
        } else {
            //Calculating sum of the given array range
            for (int i = (int) from; i < to; i++) {
                count = count + array[i];
            }
        }
        //Waiting for the result
        for (RecursiveTask<Long> task : forks) {
            count = count + task.join();
        }
        return count;
    }
}

