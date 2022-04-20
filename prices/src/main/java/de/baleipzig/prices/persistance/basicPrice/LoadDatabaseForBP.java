package de.baleipzig.prices.persistance.basicPrice;

import de.baleipzig.prices.persistance.discountPrice.LoadDatabaseForDP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Configuration
public class LoadDatabaseForBP {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabaseForDP.class);

    @Bean
    CommandLineRunner initDatabase(BasicPriceRepository basicPriceRepository) {

        OffsetDateTime o1 = OffsetDateTime.now();
        OffsetDateTime o2 = OffsetDateTime.of(2026, 4, 9, 20, 15, 45, 345875000, ZoneOffset.of("+07:00"));

        return args -> {
            final BasicPrice b1 = new BasicPrice(o1 , o2, 2000 );
            saveIfNotExists(basicPriceRepository, b1);

        };
    }

    private void saveIfNotExists(BasicPriceRepository repository, BasicPrice basicPrice) {
        if (!repository.exists(Example.of(basicPrice))) {
            log.info("Preloading {}.", repository.save(basicPrice));
        }
        else {
            log.info("Canceled Preloading {}, already exists.", basicPrice);
        }
    }


}