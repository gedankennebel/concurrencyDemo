package forkjoinbenchmark;

import java.math.BigDecimal;

public class Algorithm {

    public static StatisticResult computeResult(Customer[] customers, int start, int end) {
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
}
