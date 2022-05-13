package de.baleipzig.products.services;

import de.baleipzig.eshop.api.dto.ImageDTO;
import de.baleipzig.eshop.api.dto.InformationDTO;
import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.entities.Image;
import de.baleipzig.products.entities.Information;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.services.interfaces.MapperService;
import de.baleipzig.products.services.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class MapperServiceImpl implements MapperService {

    private final ProductService productService;

    public MapperServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Product toEntity(ProductDTO toMap) {
        return new Product(
                toMap.id(),
                ProductType.valueOf(toMap.productType().toUpperCase()),
                toMap.name());
    }


    @Override
    public ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getProductType().name(),
                product.getName());
    }

    @Override
    public ImageDTO toDTO(Image image) {
        return new ImageDTO(image.getId(),
                image.getProduct().getId(),
               image.getImageData());
    }

    @Override
    public Image toEntity(ImageDTO dto) {
        return new Image(dto.id(),
                this.productService.getByID(dto.productID()),
                dto.content());
    }

    @Override
    public InformationDTO toDTO(Information entity) {
        return new InformationDTO(entity.getId(),
                entity.getProduct().getId(),
                entity.getTechDoc(),
                entity.getAdditionalInformation());
    }

    @Override
    public Information toEntity(InformationDTO dto) {
        return new Information(dto.id(),
                this.productService.getByID(dto.productID()),
                dto.techDoc(),
                dto.additionalInformation());
    }
}
