package de.baleipzig.prices.service;

import de.baleipzig.prices.services.interfaces.ProduktService;
import de.baleipzig.prices.entities.BasicPrice;
import de.baleipzig.prices.entities.DiscountPrice;
import de.baleipzig.prices.repositories.BasicPriceRepository;
import de.baleipzig.prices.repositories.DiscountPriceRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.IntStream;



public class PriceServiceImplTest {

    @BeforeAll
    static void initDBforBP(@Autowired BasicPriceRepository bpRepository) {

        /**
         * Product-id?
         */
        IntStream.range(1, 300)
                .mapToObj(i -> new BasicPrice(1L, OffsetDateTime.of(LocalDateTime.of(2017, 05, 12, 05, 45),
                        ZoneOffset.ofHoursMinutes(6, 30)), OffsetDateTime.of(LocalDateTime.of(2017, 10, 12, 05, 45),
                        ZoneOffset.ofHoursMinutes(6, 30)), 2000)).forEach(bpRepository::save);
    }

    @BeforeAll
    static void initDBforDP(@Autowired DiscountPriceRepository dpRepository) {
        IntStream.range(1, 300)
                .mapToObj(i -> new DiscountPrice(1L, OffsetDateTime.of(LocalDateTime.of(2017, 06, 12, 05, 45),
                        ZoneOffset.ofHoursMinutes(6, 30)), OffsetDateTime.of(LocalDateTime.of(2017, 07, 12, 05, 45),
                        ZoneOffset.ofHoursMinutes(6, 30)), 2000)).forEach(dpRepository::save);
    }

    @Test
    void testServiceForCheckingRequest(@Autowired ProduktService service) {

        /**
         * - Überprüfe ob für das ProduktXY ein Preis existiert
         * - Überprüfe zuerst den Discountpreis für den gegebenen Zeitraum
         * - existiert keiner -> nimm Basicpreis
         * - Ausgabe: DP oder BP
         */

    }
    }


