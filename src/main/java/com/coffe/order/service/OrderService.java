package com.coffe.order.service;

import com.coffe.order.entity.Drink;
import com.coffe.order.entity.Order;
import com.coffe.order.entity.OrderItem;
import com.coffe.order.entity.Topping;
import com.coffe.order.helper.PriceHelper;
import com.coffe.order.model.Cart;
import com.coffe.order.model.OrderDto;
import com.coffe.order.model.OrderItemRequestBody;
import com.coffe.order.model.PriceDto;
import com.coffe.order.repository.DrinkRepository;
import com.coffe.order.repository.OrderRepository;
import com.coffe.order.repository.ToppingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final DrinkRepository drinkRepository;
    private final ToppingRepository toppingRepository;
    private final PriceHelper priceHelper;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(DrinkRepository drinkRepository, ToppingRepository toppingRepository,
                        OrderRepository orderRepository) {
        this.drinkRepository = drinkRepository;
        this.toppingRepository = toppingRepository;
        this.orderRepository = orderRepository;
        this.priceHelper = new PriceHelper();
        this.modelMapper = new ModelMapper();
    }

    public OrderDto createOrder(Cart requestBody) {
        Order order = new Order();

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequestBody orderItemRequestBody : requestBody.getItems()) {
            Drink drink = drinkRepository.findById(orderItemRequestBody.getDrinkId())
                    .orElseThrow(() -> new EntityNotFoundException("Drink with id " + orderItemRequestBody.getDrinkId() + " not found"));
            Topping topping = null;
            if (Objects.nonNull(orderItemRequestBody.getToppingId())) {
                topping = toppingRepository.findById(orderItemRequestBody.getToppingId())
                        .orElseThrow(() -> new EntityNotFoundException("Topping with id " + orderItemRequestBody.getToppingId() + " not found"));
                ;
            }
            OrderItem orderItem = new OrderItem(drink, topping);
            orderItems.add(orderItem);
        }


        PriceDto priceDto = priceHelper.calculateLatestPrice(orderItems);
        log.info("Latest Price: " + priceDto.getDiscountedPrice());
        log.info("Price before discount: " + priceDto.getTotalPrice());
        order.setItems(orderItems);
        order.setTotalPrice(priceDto.getTotalPrice());
        order.setDiscountedPrice(priceDto.getDiscountedPrice());

        Order savedOrder = orderRepository.save(order);
        return fromOrderToOrderDto(savedOrder);
    }

    private OrderDto fromOrderToOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}

