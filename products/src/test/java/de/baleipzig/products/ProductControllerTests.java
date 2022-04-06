package de.baleipzig.products;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.baleipzig.products.persistance.Product;
import de.baleipzig.products.persistance.ProductType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class ProductControllerTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static JSONObject productJsonObject;
    private static String personServiceUrl;
    private static RestTemplate restTemplate;
    private static HttpHeaders headers;

    @BeforeAll
    public static void runBeforeAllTestMethods() throws JSONException {
        personServiceUrl = "http://localhost:80/products"; // POST request

        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        productJsonObject = new JSONObject();
        productJsonObject.put("id", 353122);
        productJsonObject.put("productType", ProductType.ELECTRONICS);
        productJsonObject.put("name", "Headset");
        productJsonObject.put("eigenschaft", "Kabellos");
    }


    @Test
    void testPostMapping() throws IOException {
        HttpEntity<String> request = new HttpEntity<>(productJsonObject.toString(), headers);

        Product productResult = restTemplate.postForObject(personServiceUrl, request, Product.class);
        Product productExpected = objectMapper.readValue(productJsonObject.toString(), Product.class);

        assertNotNull(productResult);
        assertEquals(productExpected, productResult);
    }

    @Test
    void testGetMapping () throws JsonProcessingException {

        HttpEntity<String> request = new HttpEntity<>(productJsonObject.toString(), headers);
        restTemplate.postForObject(personServiceUrl, request, Product.class);

        Product productResult = restTemplate.getForObject(personServiceUrl + "/353122", Product.class);
        Product productExpected = objectMapper.readValue(productJsonObject.toString(), Product.class);

        assertNotNull(productResult);
        assertEquals(productExpected, productResult);
    }

    // WARUM FUNKTIONIERT DER TEST NICHT
    @Test
    void testDeleteMapping() {
        HttpEntity<String> request = new HttpEntity<>(productJsonObject.toString(), headers);
        restTemplate.postForObject(personServiceUrl, request, Product.class);

        restTemplate.delete(personServiceUrl + "/353122");
        ResponseEntity<Product> response = restTemplate.getForEntity(personServiceUrl + "/353122", Product.class);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testPutMapping() throws JSONException, JsonProcessingException {
        HttpEntity<String> request = new HttpEntity<>(productJsonObject.toString(), headers);
        restTemplate.postForObject(personServiceUrl, request, Product.class);

        // aktuallisierten Datenbankeintrag erstellen
        JSONObject newProductJsonObject = new JSONObject();
        newProductJsonObject.put("id", 353122);
        newProductJsonObject.put("productType", ProductType.ELECTRONICS);
        newProductJsonObject.put("name", "Headset");
        newProductJsonObject.put("eigenschaft", "Bluetoth");

        // Datenbankeintrag Updaten
        HttpEntity<String> newRequest = new HttpEntity<>(newProductJsonObject.toString(), headers);
        restTemplate.put(personServiceUrl + "/353122", newRequest);

        // ergebnis überprüfen
        Product productResult = restTemplate.getForObject(personServiceUrl + "/353122", Product.class);
        Product productExpected = objectMapper.readValue(newProductJsonObject.toString(), Product.class);

        assertNotNull(productResult);
        assertEquals(productExpected, productResult);
    }
}
