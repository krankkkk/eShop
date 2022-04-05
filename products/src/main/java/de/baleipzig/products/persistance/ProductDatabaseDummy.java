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

    /**
     * Methode zum ausgeben aller Produkte in der Persistance.
     * @return eine Liste mit allen Produkten.
     */
    @Override
    public List<Product> getAll() {
        return productList;
    }

    /**
     * Methode zum Hinzufügen eines Produktes
     * @param product Das Produkt, welches Hinzugefügt werden soll.
     * @return Falls erfolgreich, gibt das Produkt welches Hinzugefügt wurde zurück. Falls nicht erfolgreich
     * gibt die Methode Null zurück
     */
    @Override
    public Product save(Product product) {
        Optional<Product> productInListFound = productList.stream()
                .filter(product1 -> product1.getId() == product.getId())
                .findAny();
        if(productInListFound.isPresent()) return null;

        productList.add(product);
        return product;
    }

    /**
     * Methode zum Updaten eines Produktes in der Persistance
     * @param product das Produkt, welches Updated werden soll
     * @return gibt das Produkt zurück, falls erfolgreich, und Null falls nicht erfolgreich
     */
    @Override
    public Product update(Product product) {
        // finde den Index des Objektes das updated werde soll in der Liste
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId() == product.getId()) {
                productList.set(i, product);
                return product;
            }
        }
        return null;
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
