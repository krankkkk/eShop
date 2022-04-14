package de.baleipzig.products;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductControllerValidationTest {

    private static final String productServiceUrl = "http://localhost:80/products";
    private static RestTemplate restTemplate;

    @BeforeAll
    public static void runBeforeAllTestMethods() {
        restTemplate = new RestTemplate();

    }

    @Test
    void testProductTypeValidation() {
        // Datenbankeintrag mit ungültigem Produkttyp erstellen
        ProductDTO product = new ProductDTO( "ungültig", "maus", "kabel");

        HttpEntity<ProductDTO> requestBody = new HttpEntity<>(product);

        assertThrows(HttpClientErrorException.BadRequest.class, () -> restTemplate.exchange(productServiceUrl,
                HttpMethod.POST,
                requestBody,
                Long.class));

    }
}
