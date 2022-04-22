package de.baleipzig.prices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "de.baleipzig.prices",
        "de.baleipzig.prices.controller",
        "de.baleipzig.prices.repositories",
        "de.baleipzig.prices.services"
})
@EntityScan(basePackages = {"de.baleipzig.prices.entities"})
@EnableJpaRepositories(basePackages = {"de.baleipzig.prices.repositories"})
public class PricesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricesApplication.class, args);
    }
}
