package de.baleipzig.prices.entities;

import de.baleipzig.prices.repositories.BasicPriceRepository;
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

    private static final Logger log = LoggerFactory.getLogger(LoadDatabaseForBP.class);

    @Bean
    CommandLineRunner initBPDatabase(BasicPriceRepository basicPriceRepository) {

        OffsetDateTime startTime = OffsetDateTime.of(2021, 4, 9, 20, 15, 45, 345875000, ZoneOffset.of("+07:00"));
        OffsetDateTime EndTime = OffsetDateTime.of(2026, 4, 9, 20, 15, 45, 345875000, ZoneOffset.of("+07:00"));

        return args -> {
            final BasicPrice b1 = new BasicPrice(1L, startTime, EndTime, 2000);
            saveIfNotExists(basicPriceRepository, b1);

        };
    }

    private void saveIfNotExists(BasicPriceRepository repository, BasicPrice basicPrice) {
        if (!repository.exists(Example.of(basicPrice))) {
            log.info("Preloading {}.", repository.save(basicPrice));
        } else {
            log.info("Canceled Preloading {}, already exists.", basicPrice);
        }
    }


}