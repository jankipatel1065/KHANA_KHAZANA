package com.humber.khana_khazana.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private long itemId;

    @ManyToOne
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;


    @Column(name = "QUANTITY")
    private int quantity;
}
