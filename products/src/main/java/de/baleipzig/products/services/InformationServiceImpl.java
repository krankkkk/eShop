package de.baleipzig.products.services;

import de.baleipzig.products.entities.Information;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.exceptions.ImageNotFoundException;
import de.baleipzig.products.exceptions.InformationNotFoundException;
import de.baleipzig.products.repositories.InformationRepository;
import de.baleipzig.products.services.interfaces.InformationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InformationServiceImpl implements InformationService {

    private final InformationRepository repository;

    public InformationServiceImpl(final InformationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Information getInformationByProduct(Product product) {

        return this.repository.getInformationByProduct(product)
                .orElseThrow(() -> new InformationNotFoundException("No Information for Product %s found".formatted(product)));
    }

    @Override
    public Information getInformationByID(long imageID) {
        if (!this.repository.existsById(imageID)) {
            throw new InformationNotFoundException(imageID);
        }

        return this.repository.getById(imageID);
    }

    @Override
    public Information newInformation(Information toSave) {
        return this.repository.save(new Information(toSave.getProduct(), toSave.getTechDoc(), toSave.getAdditionalInformation()));
    }

    @Override
    public Information updateInformation(Information toUpdate) {
        deleteInformation(toUpdate);
        return newInformation(toUpdate);
    }

    @Override
    public void deleteInformation(Information toDelete) {
        Long id = toDelete.getId();

        if (id == null) {
            throw new ImageNotFoundException(InformationNotFoundException.NULL_REASON);
        }

        if (!this.repository.existsById(id)) {
            throw new InformationNotFoundException(id);
        }

        this.repository.deleteById(id);
    }

    @Override
    public void deleteByProduct(Product product) {
        this.repository.deleteAllByProduct(product);
    }
}
