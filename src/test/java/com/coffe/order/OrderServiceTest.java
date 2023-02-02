package com.coffe.order;

import com.coffe.order.entity.Drink;
import com.coffe.order.entity.Order;
import com.coffe.order.entity.OrderItem;
import com.coffe.order.entity.Topping;
import com.coffe.order.helper.Mapper;
import com.coffe.order.helper.PriceHelper;
import com.coffe.order.model.Cart;
import com.coffe.order.model.OrderDto;
import com.coffe.order.model.OrderItemRequestBody;
import com.coffe.order.model.PriceDto;
import com.coffe.order.repository.DrinkRepository;
import com.coffe.order.repository.OrderRepository;
import com.coffe.order.repository.ToppingRepository;
import com.coffe.order.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private final DrinkRepository drinkRepository = mock(DrinkRepository.class);
    private final ToppingRepository toppingRepository = mock(ToppingRepository.class);
    private final PriceHelper priceHelper = mock(PriceHelper.class);
    private final ModelMapper modelMapper = mock(ModelMapper.class);
    private final OrderService orderService = new OrderService(drinkRepository, toppingRepository, orderRepository);

    @BeforeEach
    public void init() {
    }

    @Test
    public void testCreateOrder_GivenValidRequestBody_ShouldReturnOrderDto() {
        Cart requestBody = new Cart();
        requestBody.setItems(List.of(
                new OrderItemRequestBody(1L, 1L),
                new OrderItemRequestBody(2L, null)
        ));

        Drink drink1 = new Drink();
        drink1.setId(1L);
        drink1.setPrice(10.00);
        when(drinkRepository.findById(1L)).thenReturn(Optional.of(drink1));

        Topping topping1 = new Topping();
        topping1.setId(1L);
        topping1.setPrice(10.00);
        when(toppingRepository.findById(1L)).thenReturn(Optional.of(topping1));

        Drink drink2 = new Drink();
        drink2.setId(2L);
        drink2.setPrice(20.00);
        when(drinkRepository.findById(2L)).thenReturn(Optional.of(drink2));

        Order order = new Order();
        order.setDiscountedPrice(15.00);
        order.setTotalPrice(30.00);
        order.setItems(List.of(
                new OrderItem(drink1, null),
                new OrderItem(drink2, null)
        ));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        PriceDto priceDto = new PriceDto();
        priceDto.setDiscountedPrice(15.00);
        priceDto.setTotalPrice(30.00);
        when(priceHelper.calculateLatestPrice(anyList())).thenReturn(priceDto);

        when(modelMapper.map(any(), any())).thenReturn(OrderDto.builder()
                .discountedPrice(15.00)
                .totalPrice(30.00)
                .build());

        OrderDto result = orderService.createOrder(requestBody);

        assertEquals(15.00, result.getDiscountedPrice(), 0.001);
        assertEquals(30.00, result.getTotalPrice(), 0.001);
    }

    @Test()
    public void testCreateOrder_GivenInvalidDrinkId_ShouldThrowException() {
        Cart requestBody = new Cart();
        requestBody.setItems(List.of(
                new OrderItemRequestBody(1L, 1L)
        ));

        when(drinkRepository.findById(1L)).thenReturn(Optional.empty());

        try {

            orderService.createOrder(requestBody);
        } catch (Exception e) {
            assertTrue(e instanceof EntityNotFoundException);
        }
    }
}
