package de.baleipzig.products.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.exceptions.ProductNotFoundException;
import de.baleipzig.products.services.interfaces.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

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
    private final ProductDTO testProductDTO = new ProductDTO(ProductType.FOOD.name(), "HotDog");
    private final Product testProduct = new Product(this.sampleID, ProductType.FOOD, "HotDog");

    @Test
    void shouldFetchAllProducts()
            throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add(123L);
        ids.add(345L);

        given(this.productService.getAllProducts()).willReturn(ids);


        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(ids.size())));
    }

    @Test
    void testPost_shouldSaveOneProduct()
            throws Exception {
        Long id = 3425L;

        given(this.productService.saveProduct(testProduct)).willReturn(id);

        MvcResult result = mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testProductDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(String.valueOf(id), result.getResponse().getContentAsString());
    }

    @Test
    void testGet_shouldFetchOneProduct()
            throws Exception {
        Long sampleID = 144L;
        given(this.productService.getOneProduct(sampleID)).willReturn(testProduct);

        mockMvc.perform(get("/" + sampleID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testProductDTO.name())))
                .andExpect(jsonPath("$.productType", is(testProductDTO.productType())));

    }

    @Test
    void testGet_shouldReturn404notFound()
            throws Exception {
        long sampleID = 144L;

        given(this.productService.getOneProduct(sampleID)).willThrow(new ProductNotFoundException(sampleID));

        mockMvc.perform(get("/" + sampleID))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPut_shouldUpdateOneProduct(@Autowired ObjectMapper mapper)
            throws Exception {
        Product updatedProduct = new Product(sampleID, ProductType.ELECTRONICS, "Headset");

        given(this.productService.updateProduct(updatedProduct)).willReturn(sampleID);

        MvcResult result = mockMvc.perform(put("/" + sampleID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(String.valueOf(sampleID), result.getResponse().getContentAsString());
    }

    @Test
    void testPut_shouldReturn404NotFound(@Autowired ObjectMapper mapper)
            throws Exception {

        given(this.productService.updateProduct(testProduct)).willThrow(new ProductNotFoundException(sampleID));

        mockMvc.perform(put("/" + sampleID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testProductDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPost_shouldReturn400BadRequest(@Autowired ObjectMapper mapper)
            throws Exception {

        // create non valid Product
        ProductDTO nonValidProduct = new ProductDTO("foo", "oof");

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(nonValidProduct)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete_withExistingID()
            throws Exception {
        doNothing()
                .when(this.productService)
                .deleteProduct(this.sampleID);

        mockMvc.perform(delete("/{id}", this.sampleID))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete_withNonExistingID()
            throws Exception {

        doThrow(new ProductNotFoundException(this.sampleID))
                .when(this.productService)
                .deleteProduct(this.sampleID);

        mockMvc.perform(delete("/{id}", this.sampleID))
                .andExpect(status().isNotFound());
    }
}
