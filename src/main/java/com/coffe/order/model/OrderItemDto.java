package com.coffe.order.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private DrinkDto drink;
    private ToppingDto topping;
    private Double price;
}
