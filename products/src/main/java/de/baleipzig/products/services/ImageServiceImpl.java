package de.baleipzig.products.services;

import de.baleipzig.products.entities.Image;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.exceptions.ImageNotFoundException;
import de.baleipzig.products.repositories.ImageRepository;
import de.baleipzig.products.services.interfaces.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    final ImageRepository repository;

    public ImageServiceImpl(final ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Image> getImagesByProduct(Product product) {
        return this.repository.getImagesByProduct(product);
    }

    @Override
    public Image getImageByID(long imageID) {
        if (!this.repository.existsById(imageID)) {
            throw new ImageNotFoundException(imageID);
        }

        return this.repository.getById(imageID);
    }

    @Override
    public Image newImage(Image toSave) {
        return this.repository.save(new Image(toSave.getProduct(), toSave.getImageData()));
    }

    @Override
    public Image updateImage(Image toSave) {
        deleteImage(toSave);
        return newImage(toSave);
    }

    @Override
    public void deleteImage(Image toDelete) {
        Long id = toDelete.getId();
        if (id == null) {
            throw new ImageNotFoundException(ImageNotFoundException.NULL_REASON);
        }

        if (!this.repository.existsById(id)) {
            throw new ImageNotFoundException(id);
        }

        this.repository.deleteById(id);
    }
}
