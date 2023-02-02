package com.coffe.order.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {
    private Double totalPrice;
    private Double discountedPrice;
}
