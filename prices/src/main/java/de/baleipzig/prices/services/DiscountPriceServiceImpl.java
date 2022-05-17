package de.baleipzig.prices.services;

import de.baleipzig.prices.entities.DiscountPrice;
import de.baleipzig.prices.exceptions.DiscountNotFoundException;
import de.baleipzig.prices.exceptions.PriceNotFoundException;
import de.baleipzig.prices.repositories.DiscountPriceRepository;
import de.baleipzig.prices.services.interfaces.DiscountPriceService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class DiscountPriceServiceImpl implements DiscountPriceService {

    private final DiscountPriceRepository repository;

    public DiscountPriceServiceImpl(final DiscountPriceRepository dpRepository) {
        this.repository = dpRepository;
    }


    @Override
    public DiscountPrice savePrice(final DiscountPrice price) {
        Optional<DiscountPrice> maybeEndDP = this.repository.findByEndAfterAndStartBeforeAndProductID(price.getEnd(), price.getEnd(), price.getProductID());
        if (maybeEndDP.isPresent()) {
            DiscountPrice endsToLate = maybeEndDP.get();

            this.repository.delete(endsToLate);
            this.repository.save(new DiscountPrice(endsToLate.getProductID(), endsToLate.getStart(), price.getEnd().minusNanos(1L), endsToLate.getPrice()));
        }

        Optional<DiscountPrice> maybeStartDP = this.repository.findByEndAfterAndStartBeforeAndProductID(price.getStart(), price.getStart(), price.getProductID());
        if (maybeStartDP.isPresent()) {
            DiscountPrice startsToEarly = maybeStartDP.get();

            this.repository.delete(startsToEarly);
            this.repository.save(new DiscountPrice(startsToEarly.getProductID(), price.getStart().minusNanos(1L), startsToEarly.getEnd(), startsToEarly.getPrice()));
        }

        Optional<DiscountPrice> maybeDP = this.repository.findByEndAfterAndStartBeforeAndProductID(price.getEnd(), price.getStart(), price.getProductID());
        maybeDP.ifPresent(this.repository::delete);

        return this.repository.save(new DiscountPrice(price.getProductID(), price.getStart(), price.getEnd(), price.getPrice()));
    }

    @Override
    public DiscountPrice getPriceByProductID(final long productID,
                                             final OffsetDateTime at) {
        return repository.findByEndAfterAndStartBeforeAndProductID(at, at, productID)
                .orElseThrow(() -> new DiscountNotFoundException(productID));
    }

    @Override
    public DiscountPrice getPriceByID(long priceID) {
        if (this.repository.existsById(priceID)) {
            throw new PriceNotFoundException(priceID);
        }

        return this.repository.getById(priceID);
    }

    @Override
    public void deletePrice(DiscountPrice price) {
        this.repository.delete(price);
    }

    @Override
    public boolean hasPrice(long productID, OffsetDateTime at) {
        return this.repository.findByEndAfterAndStartBeforeAndProductID(at, at, productID).isPresent();
    }

    @Override
    public void deleteByProductID(long productID) {
        if(this.repository.existsByProductID(productID)){
            this.repository.deleteAllByProductID(productID);
        }
    }
}
