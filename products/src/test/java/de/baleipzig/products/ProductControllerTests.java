package de.baleipzig.products;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.baleipzig.products.persistance.ProductType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ProductControllerTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static JSONObject productJsonObject;
    private static String createPersonUrl;
    private static String updatePersonUrl;
    private static RestTemplate restTemplate;
    private static HttpHeaders headers;

    @BeforeClass
    public static void runBeforeAllTestMethods() throws JSONException {
        createPersonUrl = "http://localhost:80/products"; // POST request
        updatePersonUrl = "http://localhost:80/products"; // PUT request

        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        productJsonObject = new JSONObject();
        productJsonObject.put("id", 1);
        productJsonObject.put("productType", ProductType.ELECTRONICS);
        productJsonObject.put("name", "Headset");
        productJsonObject.put("eigenschaft", "Kabellos");
    }


    @Test
    public void testCreateAndUpdate () throws JsonProcessingException {
        HttpEntity<String> request = new HttpEntity<String>(productJsonObject.toString(), headers);

        String productResultAsJsonStr = restTemplate.postForObject(createPersonUrl, request, String.class);
        JsonNode root = objectMapper.readTree(productResultAsJsonStr);

        restTemplate.put(updatePersonUrl, request);

        assertNotNull(productResultAsJsonStr);
        assertNotNull(root);
        assertNotNull(root.path("name").asText());
    }
}
