package com.home.microservices.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipping_price")
public class Shipping {
    @Column(nullable = false)
    public String type;
    @Column(nullable = false)
    public double price;

    @Id
    @GeneratedValue
    private Long id;

}
