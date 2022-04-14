package de.baleipzig.products;


import de.baleipzig.products.persistance.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = ProductController.class)
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductDTO testProduct;

    @BeforeEach
    void setUp() {
        this.testProduct = new ProductDTO(ProductType.FOOD.name(), "HotDog", "foo");
    }

    @Test
    void shouldFetchAllProducts() {
        given().
    }
}
