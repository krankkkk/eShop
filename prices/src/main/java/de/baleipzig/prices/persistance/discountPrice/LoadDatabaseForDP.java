package de.baleipzig.prices.persistance.discountPrice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Configuration
public class LoadDatabaseForDP {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabaseForDP.class);

    @Bean
    CommandLineRunner initDatabase(DiscountPriceRepository repository) {

        OffsetDateTime o1 = OffsetDateTime.of(2022, 4, 9, 20, 15, 45, 345875000, ZoneOffset.of("+07:00"));
        OffsetDateTime o2 = OffsetDateTime.of(2023, 4, 9, 20, 15, 45, 345875000, ZoneOffset.of("+07:00"));

        return args -> {
            final DiscountPrice p1 = new DiscountPrice(0L,o1 , o2, 2000 );
            saveIfNotExists(repository, p1);

        };
    }

    private void saveIfNotExists(DiscountPriceRepository repository, DiscountPrice discountPrice) {
        if (!repository.exists(Example.of(discountPrice))) {
            log.info("Preloading {}.", repository.save(discountPrice));
        }
        else {
            log.info("Canceled Preloading {}, already exists.", discountPrice);
        }
    }


}
