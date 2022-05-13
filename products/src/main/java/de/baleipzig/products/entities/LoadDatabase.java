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
    CommandLineRunner initProducts(ProductRepository repository) {

        return args -> {
            final Product mouse = new Product(MOUSE_ID, ProductType.ELECTRONICS, "Maus");
            saveIfNotExists(repository, mouse);

            final Product broom = new Product(ProductType.HOUSEHOLD, "Besen");
            saveIfNotExists(repository, broom);

            IntStream.range(0, 30)
                    .mapToObj(i -> new Product(ProductType.FOOD, "Cheese " + i))
                    .forEach(p -> saveIfNotExists(repository, p));
        };
    }

    @Bean
    CommandLineRunner initImages(ProductRepository productRepository,
                                 ImageRepository imageRepository) {
        return args -> {
            final Product mouse = productRepository.getById(MOUSE_ID);

            try (final InputStream in = Objects.requireNonNull(getClass().getResourceAsStream("/Mouse_Example.jpg"))) {
                if (imageRepository.getImagesByProduct(mouse).isEmpty()) {
                    imageRepository.save(new Image(mouse, Base64.getEncoder().encodeToString(in.readAllBytes())));
                }
            }
        };
    }

    private <T> void saveIfNotExists(JpaRepository<T, Long> repository, T entity) {
        if (!repository.exists(Example.of(entity))) {
            log.info("Preloading {}.", repository.save(entity));
        } else {
            log.info("Canceled Preloading {}, already exists.", entity);
        }
    }

}
