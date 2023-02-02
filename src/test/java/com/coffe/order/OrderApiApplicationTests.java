package com.coffe.order;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan
class OrderApiApplicationTests {

    @Test
    void contextLoads() {

    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }

}
