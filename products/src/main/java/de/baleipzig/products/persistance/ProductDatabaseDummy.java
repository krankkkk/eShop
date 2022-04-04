package de.baleipzig.products.persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDatabaseDummy implements Dao<Product>{

    List<Product> productList = new ArrayList<>();

    public ProductDatabaseDummy() {

        productList.add(new Product(ProductType.FOOD, 1234, "Schnitzel", "500 Gramm"));
        productList.add(new Product(ProductType.ELECTRONICS, 9876,"Notebook", "5 GB RAM"));
    }

    @Override
    public Optional<Product> get(long id) {
        return productList.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return productList;
    }

    @Override
    public void save(Product product) {
        productList.add(product);
    }

    @Override
    public void update(long id, Product product) {
        // finde den Index des Objektes das updated werde soll in der Liste
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId() == id) productList.set(i, product);
        }
    }

    @Override
    public void delete(long todelete) {

        productList.removeIf(product -> product.getId() == todelete);
    }

    public void printList(){
        System.out.println("PRODUKTLISTE------------------------------");
        for (Product p : productList) {
            System.out.println(p.getName());
        }
    }
}
