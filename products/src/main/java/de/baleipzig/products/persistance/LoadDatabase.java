package de.baleipzig.products.persistance;

import de.baleipzig.eshop.api.foo.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;


@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {

        return args -> {
            final Product mouse = new Product(ProductType.ELECTRONICS, "Maus", "Kabellos");
            saveIfNotExists(repository, mouse);

            final Product broom = new Product(ProductType.HOUSEHOLD, "Besen", "1,5m ");
            saveIfNotExists(repository, broom);
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
