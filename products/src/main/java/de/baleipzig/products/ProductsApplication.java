package de.baleipzig.products;

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
        "de.baleipzig.products",
        "de.baleipzig.products.controller",
        "de.baleipzig.products.repositories",
        "de.baleipzig.products.services"
})
@EntityScan(basePackages = {"de.baleipzig.products.entities"})
@EnableJpaRepositories(basePackages = {"de.baleipzig.products.repositories"})
public class ProductsApplication extends SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer webConfig(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("*");
            }
        };
    }
}
