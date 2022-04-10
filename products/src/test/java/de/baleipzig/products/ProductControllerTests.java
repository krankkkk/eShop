package de.baleipzig.products;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.baleipzig.products.persistance.Product;
import de.baleipzig.products.persistance.ProductType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductControllerTests {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String productServiceUrl = "http://localhost:80/products";

    private static JSONObject productJsonObject;
    private static RestTemplate restTemplate;
    private static HttpHeaders headers;

    @BeforeAll
    public static void runBeforeAllTestMethods() throws JSONException {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        productJsonObject = new JSONObject();
        productJsonObject.put("id", null);
        productJsonObject.put("productType", ProductType.ELECTRONICS);
        productJsonObject.put("name", "Headset");
        productJsonObject.put("eigenschaft", "Kabellos");
    }

    @Test
    void testGetProductsList() throws URISyntaxException, JsonProcessingException {
        URI uri = new URI(productServiceUrl);

        ResponseEntity<List<ProductDTO>> response= restTemplate
                .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDTO>>() {});

        List<ProductDTO> products = response.getBody();

        // verify request succeed
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(products.size() > 0 );
    }


    @Test
    void testPostMapping() {
        HttpEntity<String> request = new HttpEntity<>(productJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(productServiceUrl, request, String.class);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
    }

    @Test
    void testGetMapping () throws JsonProcessingException, URISyntaxException {

        Product firstProduct = getFirstProduct();

        URI uri = new URI(productServiceUrl + "/" + firstProduct.getId());
        ResponseEntity<ProductDTO> response= restTemplate
                .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<ProductDTO>() {});

        ProductDTO product = response.getBody();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Objects.requireNonNull(product.id()), firstProduct.getId());
    }

    private Product getFirstProduct() throws URISyntaxException, JsonProcessingException {
        URI uri = new URI(productServiceUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri,String.class);
        List<Product> myProducts = objectMapper.readValue(result.getBody(), new TypeReference<>() {});
        return myProducts.stream().findFirst().orElse(null);
    }

    @Test
    void testDeleteMapping() throws URISyntaxException, JsonProcessingException {

        Product firstProduct  = getFirstProduct();

        restTemplate.delete(productServiceUrl + "/" + firstProduct.getId());

        assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity(productServiceUrl + "/" + firstProduct.getId(), Product.class));
    }

    @Test
    void testPutMapping() throws JSONException, JsonProcessingException, URISyntaxException {
        Product firstProduct = getFirstProduct();

        // aktuallisierten Datenbankeintrag erstellen
        JSONObject newProductJsonObject = new JSONObject();
        newProductJsonObject.put("id", null);
        newProductJsonObject.put("productType", ProductType.ELECTRONICS);
        newProductJsonObject.put("name", "Headset");
        newProductJsonObject.put("eigenschaft", "Bluetoth");

        // Datenbankeintrag Updaten
        HttpEntity<String> newRequest = new HttpEntity<>(newProductJsonObject.toString(), headers);
        restTemplate.put(productServiceUrl + "/" + firstProduct.getId(), newRequest);

        // ergebnis überprüfen
        URI uri = new URI(productServiceUrl + "/" + firstProduct.getId());
        ResponseEntity<String> result = restTemplate.getForEntity(uri,String.class);
        Product product = restTemplate.getForObject(uri, Product.class);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Headset", Objects.requireNonNull(product.getName()));
        assertEquals(ProductType.ELECTRONICS, product.getProductType());
        assertEquals("Bluetoth", product.getEigenschaft());
    }
}
