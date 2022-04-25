package de.baleipzig.products.entities;

import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;

import java.util.stream.IntStream;


@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {

        return args -> {
            final Product mouse = new Product(ProductType.ELECTRONICS, "Maus");
            saveIfNotExists(repository, mouse);

            final Product broom = new Product(ProductType.HOUSEHOLD, "Besen");
            saveIfNotExists(repository, broom);

            IntStream.range(0, 30)
                    .mapToObj(i -> new Product(ProductType.FOOD, "Cheese " + i))
                    .forEach(p -> saveIfNotExists(repository, p));
        };
    }

    private void saveIfNotExists(ProductRepository repository, Product product) {
        if (!repository.exists(Example.of(product))) {
            log.info("Preloading {}.", repository.save(product));
        } else {
            log.info("Canceled Preloading {}, already exists.", product);
        }
    }

}
