package de.baleipzig.prices.persistance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PriceRepository repository) {

        OffsetDateTime o1 = OffsetDateTime.now();
        OffsetDateTime o2 = OffsetDateTime.of(2023, 4, 9, 20, 15, 45, 345875000, ZoneOffset.of("+07:00"));

        return args -> {
            final Price p1 = new Price(0L,o1 , o2, 2000 );
            saveIfNotExists(repository, p1);

        };
    }

    private void saveIfNotExists(PriceRepository repository, Price price) {
        if (!repository.exists(Example.of(price))) {
            log.info("Preloading {}.", repository.save(price));
        }
        else {
            log.info("Canceled Preloading {}, already exists.", price);
        }
    }


}
