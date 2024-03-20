package model;

import java.time.LocalDateTime;

public class Rate {

//    private Currency currency;
    private double course;
    LocalDateTime localDateTime;


    public Rate(double course) {
        this.course = course;
        localDateTime = LocalDateTime.now();
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getCourse() {
        return course;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setCourse(double course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "CurrencyExchange{" +
                "currency=" + currency +
                ", course=" + course +
                '}';
    }
}
