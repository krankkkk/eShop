package de.baleipzig.prices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    @Bean
    public WebMvcConfigurer webConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("*");
            }
        };
    }
}
