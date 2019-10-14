package com.codegym.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name,price,amount;

    private LocalDate dayCreate;

    @Lob
    private String description;

    public Product() {
    }

    public Product(String name, String price, String amount, LocalDate dayCreate, String description) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.dayCreate = dayCreate;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public LocalDate getDayCreate() {
        return dayCreate;
    }

    public void setDayCreate(LocalDate dayCreate) {
        this.dayCreate = dayCreate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
