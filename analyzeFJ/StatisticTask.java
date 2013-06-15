package analyzeFJ;

import java.math.BigDecimal;
import java.util.concurrent.RecursiveTask;

public class StatisticTask extends RecursiveTask<StatisticResult> {

    private final static int THRESHOLD = 100_000;

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
        if ((end - start) < THRESHOLD) {
            return computeDirectly();
        } else {
            final int split = (end - start) / 2;
            final StatisticTask left = new StatisticTask(customers, start, start + split);
            left.fork();
            final StatisticTask right = new StatisticTask(customers, start + split, end);
            return joinResult(right.compute(), left.join());
        }
    }

    private StatisticResult computeDirectly() {
        BigDecimal totalSales = new BigDecimal(0);
        Customer maxCustomer = new Customer("", new BigDecimal(-1));
        for (int i = start; i < end; i++) {
            if (customers[i].getSales().compareTo(maxCustomer.getSales()) > 0) {
                maxCustomer = customers[i];
            }
            totalSales = totalSales.add(customers[i].getSales());
        }
        return new StatisticResult(maxCustomer, totalSales);
    }

    private StatisticResult joinResult(StatisticResult leftResult, StatisticResult rightResult) {
        BigDecimal totalSales = leftResult.getTotalSale().add(rightResult.getTotalSale());
        if (leftResult.getHighestCustomer().getSales().compareTo(rightResult.getHighestCustomer().getSales()) > 0) {
            return new StatisticResult(leftResult.getHighestCustomer(), totalSales);
        } else {
            return new StatisticResult(rightResult.getHighestCustomer(), totalSales);
        }
    }
}
