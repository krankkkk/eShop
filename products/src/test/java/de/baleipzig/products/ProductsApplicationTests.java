package de.baleipzig.products;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class ProductsApplicationTests {

    @Test
    void contextLoads() {
    }
}
