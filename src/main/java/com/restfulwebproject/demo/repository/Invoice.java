package com.restfulwebproject.demo.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Invoice {

    private int id;
    private String name;
    private String adres;
    private LocalDate date;
    private BigDecimal total;

    public Invoice() {
    }

    public Invoice(String name, String address, LocalDate date, BigDecimal total)
    {
        this(0, name, address, date, total);
    }

    public Invoice(int id, String name, String adres, LocalDate date, BigDecimal total) {
        this.id = id;
        this.name = name;
        this.adres = adres;
        this.date = date;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adres='" + adres + '\'' +
                ", date=" + date +
                ", total=" + total +
                '}';
    }
}
