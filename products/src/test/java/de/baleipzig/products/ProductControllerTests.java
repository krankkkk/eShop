package de.baleipzig.products;

import de.baleipzig.products.persistance.Product;
import de.baleipzig.products.persistance.ProductType;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductControllerTests {

    private static final String productServiceUrl = "http://localhost:80/products";
    private static RestTemplate restTemplate;

    @BeforeAll
    public static void runBeforeAllTestMethods() {
        restTemplate = new RestTemplate();

    }

    @Test
    void testGetProductsList() throws URISyntaxException {
        URI uri = new URI(productServiceUrl);
        ResponseEntity<List<Long>> response= restTemplate
                .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        List<Long> productIDs = response.getBody();

        // verify request succeed
        assertNotNull(productIDs);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(productIDs.size() > 0 );
    }


    @Test
    void testPostMapping() {
        ProductDTO productDTO = new ProductDTO(ProductType.HOUSEHOLD.name(), "Headset", "5mKabel");
        HttpEntity<ProductDTO> request = new HttpEntity<>(productDTO);

        ResponseEntity<Long> response = restTemplate
                .exchange(productServiceUrl, HttpMethod.POST, request, Long.class);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetMapping () throws URISyntaxException {
        ProductDTO firstProduct = getFirstProduct();
        assertNotNull(firstProduct);
    }



    @Test
    void testDeleteMapping() throws URISyntaxException {
        Long firstID = getFirstID();
        restTemplate.delete(productServiceUrl + "/" + firstID);

        assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity(productServiceUrl + "/" + firstID, Product.class));
    }

    @Test
    void testPutMapping() throws URISyntaxException {
        // SPEICHERN
        ProductDTO productDTO = new ProductDTO(ProductType.HOUSEHOLD.name(), "Headset", "5mKabel");
        HttpEntity<ProductDTO> requestSavedProduct = new HttpEntity<>(productDTO);
        ResponseEntity<Long> idSavedProduct = restTemplate
                .exchange(productServiceUrl, HttpMethod.POST, requestSavedProduct, Long.class);

        // ÜBERSCHREIBEN
        ProductDTO overWriteProduct = new ProductDTO(ProductType.ELECTRONICS.name(), "Headset", "Bluetoth");
        HttpEntity<ProductDTO> requestOverwriteProduct = new HttpEntity<>(overWriteProduct);
        restTemplate.put(productServiceUrl + "/" + idSavedProduct.getBody(), requestOverwriteProduct);


        // ÜBERSCHRIEBENEN EINTRAG MIT GET HOLEN
        URI uri = new URI(productServiceUrl + "/" + idSavedProduct.getBody());
        ResponseEntity<ProductDTO> overwrittenProduct = restTemplate
                .exchange(uri, HttpMethod.GET, null, ProductDTO.class);

        ProductDTO result = overwrittenProduct.getBody();

        assertEquals(200, overwrittenProduct.getStatusCodeValue());
        assertNotNull(result);
        assertEquals(overWriteProduct, result);
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

    private ProductDTO getFirstProduct() throws URISyntaxException {
        Long id = getFirstID();
        ResponseEntity<ProductDTO> response = restTemplate
                .exchange(new URI(productServiceUrl + "/" + id), HttpMethod.GET, null, ProductDTO.class);

        return response.getBody();
    }

    private Long getFirstID() throws URISyntaxException {
        URI uri = new URI(productServiceUrl);
        ResponseEntity<List<Long>> response = restTemplate
                .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        List<Long> myIds = response.getBody();
        assertNotNull(myIds);
        assertTrue(myIds.size() > 0);
        return myIds.get(0);
    }
}
