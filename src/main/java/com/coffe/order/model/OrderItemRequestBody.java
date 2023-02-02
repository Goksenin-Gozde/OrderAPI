package com.coffe.order.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequestBody {
    private Long drinkId;
    private Long toppingId;
}
