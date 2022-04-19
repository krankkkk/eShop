package de.baleipzig.products;

import de.baleipzig.products.mapping.MapperFactory;
import de.baleipzig.products.mapping.MapperFactoryImplementation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "de.baleipzig.products",
        "de.baleipzig.products.controller",
        "de.baleipzig.products.repositories",
        "de.baleipzig.products.services"
})
@EntityScan(basePackages = {"de.baleipzig.products.entities"})
@EnableJpaRepositories(basePackages = {"de.baleipzig.products.repositories"})
public class ProductsApplication extends SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }
}
