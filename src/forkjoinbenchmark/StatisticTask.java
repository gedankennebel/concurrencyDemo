package forkjoinbenchmark;

import java.math.BigDecimal;
import java.util.concurrent.RecursiveTask;

public class StatisticTask extends RecursiveTask<StatisticResult> {

    private final static int THRESHOLD = 1_000;

    private final Customer[] customers;
    private int start;
    private int end;

    public StatisticTask(Customer[] customers, int start, int end) {
        this.customers = customers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected StatisticResult compute() {
        final int length = end - start;
        if ((length) < THRESHOLD) {
            return Algorithm.computeResult(customers, start, end);
        } else {
            final int split = length >> 1;
            final StatisticTask left = new StatisticTask(customers, start, start + split);
            left.fork();
            final StatisticTask right = new StatisticTask(customers, start + split, end);
            return joinResult(right.compute(), left.join());
        }
    }

    private StatisticResult joinResult(StatisticResult leftResult, StatisticResult rightResult) {
        final BigDecimal totalSales = leftResult.getTotalSale().add(rightResult.getTotalSale());
        if (leftResult.getHighestCustomer().getSales().compareTo(rightResult.getHighestCustomer().getSales()) > 0) {
            return new StatisticResult(leftResult.getHighestCustomer(), totalSales);
        } else {
            return new StatisticResult(rightResult.getHighestCustomer(), totalSales);
        }
    }
}
