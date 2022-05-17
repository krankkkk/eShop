package de.baleipzig.products.services.interfaces;

import de.baleipzig.products.entities.Image;
import de.baleipzig.products.entities.Product;

import java.util.List;

public interface ImageService {

    List<Image> getImagesByProduct(Product product);

    Image getImageByID(long imageID);

    Image newImage(Image toSave);

    Image updateImage(Image toSave);

    void deleteImage(Image toDelete);

    void deleteByProduct(Product product);
}
