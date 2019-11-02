package ro.simonamihai.a2lei;

import java.util.Date;

public class Expense {
    private Date createdAt;
    private String name;
    private double price;

    public Expense(Date createdAt, String name, double price) {
        this.createdAt = createdAt;
        this.name = name;
        this.price = price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
