package ro.simonamihai.a2lei.model;

import java.util.Date;

public class Expense {
    private int id;
    private Date createdAt;
    private String name;
    private double price;

    public Expense(Date createdAt, String name, double price) {
        this.createdAt = createdAt;
        this.name = name;
        this.price = price;
    }

    public Expense(int id, Date createdAt, String name, double price) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.price = price;
    }

    public Expense() {
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
        CharSequence date = android.text.format.DateFormat.format("yyyy-MM-dd", this.createdAt);
        return date + "\t" + name + "\t" + price;
    }

    public String getStringCreatedAt() {
        return android.text.format.DateFormat.format("yyyy-MM-dd", this.createdAt).toString();
    }


    public String getStringPrice() {
        return "" + price + " RON";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
