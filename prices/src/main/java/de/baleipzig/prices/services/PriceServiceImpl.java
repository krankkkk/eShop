package de.baleipzig.prices.services;

import de.baleipzig.prices.entities.BasicPrice;
import de.baleipzig.prices.entities.DiscountPrice;
import de.baleipzig.prices.exceptions.DiscountNotFoundException;
import de.baleipzig.prices.exceptions.NoPriceAvailableException;
import de.baleipzig.prices.exceptions.PriceNotFoundException;
import de.baleipzig.prices.repositories.BasicPriceRepository;
import de.baleipzig.prices.repositories.DiscountPriceRepository;
import de.baleipzig.prices.services.interfaces.ProduktService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class PriceServiceImpl implements ProduktService {

    private final DiscountPriceRepository dpRepository;
    private final BasicPriceRepository bpRepository;


    public PriceServiceImpl(DiscountPriceRepository dpRepository,
                            BasicPriceRepository bpRepository) {
        this.dpRepository = dpRepository;
        this.bpRepository = bpRepository;
    }


    @Override
    @Transactional
    public long saveDiscountPrice(DiscountPrice discountPrice) {
        //todo: Price der davor gilt anpassen
        //       + der danach gilt
        return dpRepository.save(discountPrice)
                .getId();
    }


    @Override
    public long saveBasicPrice(BasicPrice basicPrice) {
        //todo: Price der davor gilt anpassen
        //       + der danach gilt
        return bpRepository.save(basicPrice)
                .getId();
    }


    @Override
    public DiscountPrice getDiscountByProductID(long productID) {
        OffsetDateTime now = OffsetDateTime.now();

        return dpRepository.findByEndAfterAndStartBeforeAndProductID(now, now, productID)
                .orElseThrow(() -> new DiscountNotFoundException(productID));
    }

    @Override
    public BasicPrice getBasicPriceByProductID(long id) {
        OffsetDateTime now = OffsetDateTime.now();

        return bpRepository.findByEndAfterAndStartBeforeAndProductID(now, now, id)
                .orElseThrow(() -> new NoPriceAvailableException(id));
    }

    private void deleteDiscountPrice(long priceID) {
        dpRepository.findById(priceID)
                .orElseThrow(() -> new PriceNotFoundException(priceID));

        dpRepository.deleteById(priceID);
    }

    private void deleteBasicPrice(long priceID) {
        bpRepository.findById(priceID)
                .orElseThrow(() -> new PriceNotFoundException(priceID));

        bpRepository.deleteById(priceID);
    }

}
