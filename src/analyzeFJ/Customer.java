package analyzeFJ;

import java.math.BigDecimal;

public class Customer {

    private String name;
    private BigDecimal sales;

    public Customer() {
    }

    public Customer(String name, BigDecimal sales) {
        this.name = name;

        this.sales = sales;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }
}
