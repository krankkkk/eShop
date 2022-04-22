package de.baleipzig.products.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.exceptions.ProductNotFoundException;
import de.baleipzig.products.services.interfaces.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final Long sampleID = 144L;
    private final ProductDTO testProductDTO = new ProductDTO(this.sampleID, ProductType.FOOD.name(), "HotDog");
    private final Product testProduct = new Product(this.sampleID, ProductType.FOOD, "HotDog");

    @Test
    void testSave(@Autowired ObjectMapper mapper)
            throws Exception {

        given(this.productService.saveNewProduct(testProduct))
                .willReturn(testProduct);

        mockMvc.perform(put("/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testProductDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(this.testProduct.getId()));
    }

    @Test
    void testGet_shouldFetchOneProduct()
            throws Exception {
        given(this.productService.getByID(sampleID))
                .willReturn(testProduct);

        mockMvc.perform(get("/get/{id}", sampleID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testProductDTO.name())))
                .andExpect(jsonPath("$.productType", is(testProductDTO.productType())));

    }

    @Test
    void testGet_shouldReturn404notFound()
            throws Exception {
        long sampleID = 144L;

        given(this.productService.getByID(sampleID))
                .willThrow(new ProductNotFoundException(sampleID));

        mockMvc.perform(get("/get/{id}", sampleID))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateSuccess(@Autowired ObjectMapper mapper)
            throws Exception {
        Product updatedProduct = new Product(sampleID, ProductType.ELECTRONICS, "Headset");

        given(this.productService.updateProduct(updatedProduct))
                .willReturn(updatedProduct);

        mockMvc.perform(post("/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(this.sampleID));
    }

    @Test
    void testUpdateWithException(@Autowired ObjectMapper mapper)
            throws Exception {

        given(this.productService.updateProduct(testProduct))
                .willThrow(new ProductNotFoundException(sampleID));

        mockMvc.perform(post("/update/{id}", sampleID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testProductDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveInvalidData(@Autowired ObjectMapper mapper)
            throws Exception {
        ProductDTO nonValidProduct = new ProductDTO(null, "foo", "oof");

        mockMvc.perform(put("/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(nonValidProduct)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete_withExistingID()
            throws Exception {
        doNothing()
                .when(this.productService)
                .deleteByID(this.sampleID);

        mockMvc.perform(delete("/delete/{id}", this.sampleID))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete_withNonExistingID()
            throws Exception {

        doThrow(new ProductNotFoundException(this.sampleID))
                .when(this.productService)
                .deleteByID(this.sampleID);

        mockMvc.perform(delete("/delete/{id}", this.sampleID))
                .andExpect(status().isNotFound());
    }
}
