package forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FindWalterSolver extends RecursiveAction {
    private final String[] strings;
    private int from;
    private int to;

    public FindWalterSolver(String[] strings) {
        this.strings = strings;
        this.from = 0;
        this.to = strings.length - 1;
    }

    private FindWalterSolver(String[] strings, int from, int to) {
        this.strings = strings;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void compute() {
        if ((to - from) <= 10_000_000) {
            if (findWalter(strings, from, to)) {
                System.exit(1);
            }
        } else {
            // split
            final int midpoint = to / 2;

            final int from1 = from;
            final int to1 = midpoint;

            final int from2 = midpoint + 1;
            final int to2 = to;

            FindWalterSolver solver1 = new FindWalterSolver(strings, from1, to1);
            FindWalterSolver solver2 = new FindWalterSolver(strings, from2, to2);
            solver1.fork();
            solver2.compute();
            solver1.join();
        }
    }

    private boolean findWalter(final String[] strings, int from, int to) {
        for (int i = from; i <= to; i++) {
            if (strings[i].equals(FindWalterProblem.WALTER)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        FindWalterProblem problem = new FindWalterProblem();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        FindWalterSolver solver = new FindWalterSolver(problem.getStrings());
        ForkJoinPool forkJoinPool = new ForkJoinPool(availableProcessors);
        long start = System.currentTimeMillis();
        forkJoinPool.invoke(solver);
//        System.out.println("Found Walter in " + String.valueOf(System.currentTimeMillis() - start) + " ms");
    }
}
