package com.coffe.order.helper;

import com.coffe.order.entity.OrderItem;
import com.coffe.order.model.PriceDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PriceHelper {

    public PriceDto calculateLatestPrice(List<OrderItem> orderItems) {
        double totalPrice = 0;
        double priceWithTotalAmountDiscount = 0;
        double priceWithFreeDrinkDiscount = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getPrice();
        }

        if (totalPrice > 36) {
            priceWithTotalAmountDiscount = totalPrice * 0.75;
        }

        int drinkCount = 0;
        double minDrinkPrice = Double.MAX_VALUE;
        for (OrderItem orderItem : orderItems) {
            drinkCount++;
            minDrinkPrice = Math.min(minDrinkPrice, orderItem.getPrice());
        }
        if (drinkCount >= 3) {
            priceWithFreeDrinkDiscount = totalPrice - minDrinkPrice;
        }

        log.info("Total Price Discount: " + priceWithTotalAmountDiscount);
        log.info("Free Drink Discount: " + priceWithFreeDrinkDiscount);
        double discountedPrice = Math.min(priceWithTotalAmountDiscount, priceWithFreeDrinkDiscount);
        return PriceDto.builder()
                .discountedPrice(discountedPrice)
                .totalPrice(totalPrice)
                .build();
    }
}
