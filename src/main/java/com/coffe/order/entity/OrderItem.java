package com.coffe.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    public OrderItem(Drink drink, Topping topping){
        this.drink = drink;
        this.topping = topping;
        this.price = drink.getPrice() + (Objects.nonNull(topping) ? topping.getPrice() : 0.00);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @ManyToOne
    @JoinColumn(name = "topping_id")
    private Topping topping;

    private Double price;
}
