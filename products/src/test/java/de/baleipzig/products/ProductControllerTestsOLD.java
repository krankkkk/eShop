package de.baleipzig.products;

import de.baleipzig.products.persistance.Product;
import de.baleipzig.products.persistance.ProductRepository;
import de.baleipzig.products.persistance.ProductType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import static org.mockito.Mockito.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductControllerTestsOLD {

    private ProductController controller;
    private ProductRepository repository;
    private final MapperFactory mapperFactory = new MapperFactoryImplementation();

    @BeforeEach
    public void setUp() {
        this.repository = mock(ProductRepository.class);
        this.controller = new ProductController(this.repository, mapperFactory);
    }

    @Test
    void testPost () {
        final ProductDTO dto = new ProductDTO(ProductType.FOOD.name(), "HotDog", "foo");
        Product product = mapperFactory.mapProduct(dto);
        final Long expected = product.getId();

        when(this.repository.save(any())).thenReturn(product);

        long id = this.controller.newProduct(dto);

        assertEquals(expected,id);
        verify(this.repository, times(1)).save(product);
    }

    @Test
    void testGetAll () {
        final ProductDTO dto1 = new ProductDTO(ProductType.FOOD.name(), "HotDog", "foo");
        final ProductDTO dto2 = new ProductDTO(ProductType.FOOD.name(), "DogHot", "foo");

        List<Product> products = new ArrayList<>();
        products.add(mapperFactory.mapProduct(dto1));
        products.add(mapperFactory.mapProduct(dto2));

        when(this.repository.findAll()).thenReturn(products);

        List<Long> productIDs = this.controller.all();

        assertNotNull(productIDs);
        assertTrue(productIDs.size() > 0);
    }

    @Test
    void testGet() {
        final ProductDTO dto1 = new ProductDTO(ProductType.FOOD.name(), "HotDog", "foo");

        when(this.repository.findById(any())).thenReturn(Optional.of(mapperFactory.mapProduct(dto1)));

        ProductDTO result = this.controller.one(any());

        assertNotNull(result);
        assertEquals(dto1, result);
    }



    /**
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

     */

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
