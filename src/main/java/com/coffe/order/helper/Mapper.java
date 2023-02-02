package com.coffe.order.helper;

import com.coffe.order.entity.Order;
import com.coffe.order.model.OrderDto;
import jakarta.annotation.PostConstruct;
import lombok.Singular;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private static ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper = new ModelMapper();
    }


}
