package de.baleipzig.products.services.interfaces;

import de.baleipzig.products.entities.Information;
import de.baleipzig.products.entities.Product;

public interface InformationService {

    Information getInformationByProduct(Product product);

    Information getInformationByID(long imageID);

    Information newInformation(Information toSave);

    Information updateInformation(Information toUpdate);

    void deleteInformation(Information toDelete);
}
