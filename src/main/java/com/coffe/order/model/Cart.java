package com.coffe.order.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private List<OrderItemRequestBody> items;
}
