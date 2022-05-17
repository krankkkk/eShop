package de.baleipzig.products.services;

import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.exceptions.ProductNotFoundException;
import de.baleipzig.products.repositories.ProductRepository;
import de.baleipzig.products.services.interfaces.ImageService;
import de.baleipzig.products.services.interfaces.InformationService;
import de.baleipzig.products.services.interfaces.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ImageService imageService;
    private final InformationService informationService;

    public ProductServiceImpl(ProductRepository repository, ImageService imageService, InformationService informationService) {
        this.repository = repository;
        this.imageService = imageService;
        this.informationService = informationService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProducts(ProductType type, int offset, int maxCount) {
        try (Stream<Product> stream = this.repository.streamAllByProductType(type, Sort.by("Id").ascending())) {
            return stream.skip(offset)
                    .limit(maxCount)
                    .toList();
        }
    }

    public Product saveNewProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Product newProduct) {
        long id = Objects.requireNonNull(newProduct.getId(), "ID of product to save cannot be null");

        repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return repository.save(newProduct);
    }

    public Product getByID(long productID) {
        return repository.findById(productID)
                .orElseThrow(() -> new ProductNotFoundException(productID));
    }

    public void deleteByID(long id) {
        Product toDelete = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        this.imageService.deleteByProduct(toDelete);

        this.informationService.deleteByProduct(toDelete);

        repository.delete(toDelete);
    }

}
