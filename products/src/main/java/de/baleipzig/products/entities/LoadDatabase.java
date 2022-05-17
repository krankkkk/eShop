package de.baleipzig.products.entities;

import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.repositories.ImageRepository;
import de.baleipzig.products.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.IntStream;


@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private static final long MOUSE_ID = 1L;

    @Bean
    CommandLineRunner initProducts(ProductRepository repository,
                                   ImageRepository imageRepository) {

        return args -> {
            final Product mouse = saveIfNotExists(repository, new Product(MOUSE_ID, ProductType.ELECTRONICS, "Maus"));

            saveIfNotExists(repository, new Product(ProductType.HOUSEHOLD, "Besen"));

            IntStream.range(0, 30)
                    .mapToObj(i -> new Product(ProductType.FOOD, "Cheese " + i))
                    .forEach(p -> saveIfNotExists(repository, p));


            try (final InputStream in = Objects.requireNonNull(getClass().getResourceAsStream("/Mouse_Example.jpg"))) {
                if (imageRepository.getImagesByProduct(mouse).isEmpty()) {
                    imageRepository.save(new Image(mouse, Base64.getEncoder().encodeToString(in.readAllBytes())));
                }
            }
        };
    }

    private <T> T saveIfNotExists(JpaRepository<T, Long> repository, T entity) {
        if (!repository.exists(Example.of(entity))) {
            entity = repository.save(entity);
            log.info("Preloading {}.", entity);
        } else {
            log.info("Canceled Preloading {}, already exists.", entity);
        }

        return entity;
    }

}
