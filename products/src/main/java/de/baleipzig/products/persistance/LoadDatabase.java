package de.baleipzig.products.persistance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {

        return args -> {
            log.info("Preloading {} ", repository.save(new Product(ProductType.ELECTRONICS, 0, "Maus", "Kabellos")));
            log.info("Preloading {} ", repository.save(new Product(ProductType.HOUSEHOLD, 0, "Besen", "1,5m ")));
        };
    }

}
