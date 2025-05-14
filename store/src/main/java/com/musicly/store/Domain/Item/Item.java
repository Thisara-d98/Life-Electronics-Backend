package com.musicly.store.Domain.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int type;
    private String description;
    private String brand;
    private int quantity;
    private double price;
    // Optimistic locking field

    public Item() {
    }

    public Item(Integer id, Integer type, String brand, String description, double price, Integer quantity) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    // Add getters and setters (required for JPA)
}
