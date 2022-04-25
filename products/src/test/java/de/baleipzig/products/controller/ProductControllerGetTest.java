package de.baleipzig.products.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class ProductControllerGetTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductRepository repository;

    @Test
    void shouldFetchFirstPage(@Autowired ObjectMapper mapper)
            throws Exception {

        List<Product> list = IntStream.range(0, 100)
                .mapToObj(i -> new Product((long) i, ProductType.ELECTRONICS, Integer.toString(i)))
                .toList();

        when(this.repository.streamAllByProductType(ProductType.ELECTRONICS, Sort.by("Id").ascending()))
                .thenReturn(list.stream());

        MvcResult result = mockMvc.perform(get("/?type={type}", ProductType.ELECTRONICS.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(10)))
                .andReturn();

        List<ProductDTO> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(10, dtos.size());

        for (int i = 0; i < dtos.size(); i++) {
            assertEquals(Integer.toString(i), dtos.get(i).name());
        }
    }

    @Test
    void shouldFetchSecondPage(@Autowired ObjectMapper mapper)
            throws Exception {

        List<Product> list = IntStream.range(0, 100)
                .mapToObj(i -> new Product((long) i, ProductType.ELECTRONICS, Integer.toString(i)))
                .toList();

        when(this.repository.streamAllByProductType(ProductType.ELECTRONICS, Sort.by("Id").ascending()))
                .thenReturn(list.stream());

        MvcResult result = mockMvc.perform(get("/?type={type}&page={page}", ProductType.ELECTRONICS.name(), 2))//pages start at 0, so the second should be 2
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(10)))
                .andReturn();

        List<ProductDTO> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(10, dtos.size());

        for (int i = 0; i < dtos.size(); i++) {
            assertEquals(Integer.toString(i + 10), dtos.get(i).name());
        }
    }
}
