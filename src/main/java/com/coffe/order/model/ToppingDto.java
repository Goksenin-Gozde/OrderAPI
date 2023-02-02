package com.coffe.order.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToppingDto {
    private Long id;
    private String name;
    private Double price;
}
