package de.baleipzig.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.products.errorhandling.ProductNotFoundException;
import de.baleipzig.eshop.api.foo.ProductType;
import de.baleipzig.products.persistance.Product;
import de.baleipzig.products.rest.ProductController;
import de.baleipzig.products.rest.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final ProductDTO testProductDTO = new ProductDTO(ProductType.FOOD.name(), "HotDog", "foo");
    private final Product testProduct = new Product(ProductType.FOOD, "HotDog", "foo");

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldFetchAllProducts() throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add(123L);
        ids.add(345L);

        given(this.productService.getAllProducts()).willReturn(ids);


        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(ids.size())));
    }

    @Test
    void testPost_shouldSaveOneProduct() throws Exception {
        Long id = 3425L;

        given(this.productService.saveProduct(testProduct)).willReturn(id);

        MvcResult result = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testProductDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(String.valueOf(id), result.getResponse().getContentAsString());
    }

    @Test
    void testGet_shouldFetchOneProduct() throws Exception {
        Long sampleID = 144L;
        given(this.productService.getOneProduct(sampleID)).willReturn(testProduct);

        mockMvc.perform(get("/products/" + sampleID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testProductDTO.name())))
                .andExpect(jsonPath("$.productType", is(testProductDTO.productType())))
                .andExpect(jsonPath("$.property", is(testProductDTO.property())));

    }

    @Test
    void testGet_shouldReturn404notFound() throws Exception {

        Long sampleID = 144L;

        given(this.productService.getOneProduct(sampleID)).willThrow(new ProductNotFoundException(sampleID));

        mockMvc.perform(get("/products/" + sampleID))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPut_shouldUpdateOneProduct() throws Exception {

        Long sampleID = 144L;
        Product updatedProduct = new Product(ProductType.ELECTRONICS, "Headset", "foo");

        given(this.productService.updateProduct(updatedProduct, sampleID)).willReturn(sampleID);

        MvcResult result = mockMvc.perform(put("/products/" + sampleID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(String.valueOf(sampleID), result.getResponse().getContentAsString());
    }

    @Test
    void testPut_shouldReturn404NotFound() throws Exception {
        Long sampleID = 144L;

        given(this.productService.updateProduct(testProduct, sampleID)).willThrow(new ProductNotFoundException(sampleID));

        mockMvc.perform(put("/products/" + sampleID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testProductDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPost_shouldReturn400BadRequest() throws Exception {

        // create non valid Product
        ProductDTO nonValidProduct = new ProductDTO("foo", "oof", "hui");

        mockMvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(nonValidProduct)))
                .andExpect(status().isBadRequest());
    }

    // keine ahnung wie man die delete Methode testen soll
}
