package forkjoinbenchmark;

import java.math.BigDecimal;

public class StatisticResult {

    private Customer highestCustomer;
    private BigDecimal totalSale;
    private long calculationTime;

    public StatisticResult(Customer highestCustomer, BigDecimal totalSale) {
        this.highestCustomer = highestCustomer;
        this.totalSale = totalSale;
    }

    public StatisticResult(Customer highestCustomer, BigDecimal totalSale, long calculationTime) {
        this.highestCustomer = highestCustomer;
        this.totalSale = totalSale;
        this.calculationTime = calculationTime;
    }

    public long getCalculationTime() {
        return calculationTime;
    }

    public void setCalculationTime(long calculationTime) {
        this.calculationTime = calculationTime;
    }

    public Customer getHighestCustomer() {
        return highestCustomer;
    }

    public void setHighestCustomer(Customer highestCustomer) {
        this.highestCustomer = highestCustomer;
    }

    public BigDecimal getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(BigDecimal totalSale) {
        this.totalSale = totalSale;
    }
}
