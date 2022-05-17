package de.baleipzig.prices.services;

import de.baleipzig.prices.entities.BasicPrice;
import de.baleipzig.prices.exceptions.NoPriceAvailableException;
import de.baleipzig.prices.exceptions.PriceNotFoundException;
import de.baleipzig.prices.repositories.BasicPriceRepository;
import de.baleipzig.prices.services.interfaces.BasicPriceService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class BasicPriceServiceImpl implements BasicPriceService {

    private final BasicPriceRepository repository;

    public BasicPriceServiceImpl(BasicPriceRepository bpRepository) {
        this.repository = bpRepository;
    }

    @Override
    public BasicPrice savePrice(BasicPrice price) {
        Optional<BasicPrice> maybeEndBP = this.repository.findByEndAfterAndStartBeforeAndProductID(price.getEnd(), price.getEnd(), price.getProductID());
        if (maybeEndBP.isPresent()) {
            BasicPrice endsToLate = maybeEndBP.get();

            this.repository.delete(endsToLate);
            this.repository.save(new BasicPrice(endsToLate.getProductID(), endsToLate.getStart(), price.getEnd().minusNanos(1L), endsToLate.getPrice()));
        }

        Optional<BasicPrice> maybeStartBP = this.repository.findByEndAfterAndStartBeforeAndProductID(price.getStart(), price.getStart(), price.getProductID());
        if (maybeStartBP.isPresent()) {
            BasicPrice startsToEarly = maybeStartBP.get();

            this.repository.delete(startsToEarly);
            this.repository.save(new BasicPrice(startsToEarly.getProductID(), price.getStart().minusNanos(1L), startsToEarly.getEnd(), startsToEarly.getPrice()));
        }

        this.repository.findByEndAfterAndStartBeforeAndProductID(price.getEnd(), price.getStart(), price.getProductID())
                .ifPresent(this.repository::delete);

        return this.repository.save(new BasicPrice(price.getProductID(), price.getStart(), price.getEnd(), price.getPrice()));
    }

    @Override
    public BasicPrice getPriceByProductID(long productID, OffsetDateTime at) {
        return repository.findByEndAfterAndStartBeforeAndProductID(at, at, productID)
                .orElseThrow(() -> new NoPriceAvailableException(productID));
    }

    @Override
    public BasicPrice getPriceByID(long priceID) {
        if (!this.repository.existsById(priceID)) {
            throw new PriceNotFoundException(priceID);
        }

        return this.repository.getById(priceID);
    }

    @Override
    public void deletePrice(BasicPrice price) {
        this.repository.delete(price);
    }

    @Override
    public void deleteByProductID(long productID) {
        if(this.repository.existsByProductID(productID)){
            this.repository.deleteByProductID(productID);
        }

    }
}
