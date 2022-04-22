package de.baleipzig.products.services;

import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.repositories.ProductRepository;
import de.baleipzig.products.services.interfaces.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class ProductServiceImplTest {

    @BeforeAll
    static void initDB(@Autowired ProductRepository repository) {
        IntStream.range(1, 300)
                .mapToObj(i -> new Product(ProductType.ELECTRONICS, "Product %d".formatted(i)))
                .forEach(repository::save);
    }

    @Test
    void testServiceFromTo(@Autowired ProductService service) {
        int maxCount = 100;
        List<Product> products = service.getProducts(ProductType.ELECTRONICS, 100, maxCount);

        assertEquals(100, products.size());//checks correct Size

        for (int i = 0; i < products.size(); i++) {
            Product oneProduct = products.get(i);

            assertEquals("Product %d".formatted(100 + i), oneProduct.getName());//checks sorting
            assertEquals(ProductType.ELECTRONICS, oneProduct.getProductType());//checks selection
        }
    }
}