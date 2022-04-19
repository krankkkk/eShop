package de.baleipzig.products;

import de.baleipzig.products.mapping.MapperFactory;
import de.baleipzig.products.mapping.MapperFactoryImplementation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

    @Bean
    public static MapperFactory getFactory() {
        return new MapperFactoryImplementation();
    }

}
