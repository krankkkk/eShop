package de.baleipzig.prices;

import de.baleipzig.prices.persistance.basicPrice.BasicPrice;
import de.baleipzig.prices.persistance.basicPrice.BasicPriceRepository;
import de.baleipzig.prices.persistance.discountPrice.DiscountPrice;
import de.baleipzig.prices.persistance.discountPrice.DiscountPriceRepository;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceService {

    private final DiscountPriceRepository dP_repository;
    private final BasicPriceRepository bP_repository;


    public PriceService(DiscountPriceRepository dP_repository, BasicPriceRepository bP_repository) {
        this.dP_repository = dP_repository;
        this.bP_repository = bP_repository;
    }

    public List<Long> getAllDiscountPrices(){
        List<DiscountPrice> dPrices = dP_repository.findAll();
        return dPrices.stream().map(AbstractPersistable::getId).toList();
    }

    public List<Long> getAllBasicPrices(){
        List<BasicPrice> bPrices = bP_repository.findAll();
        return bPrices.stream().map(AbstractPersistable::getId).toList();
    }

    public Long saveDiscountPrice ( DiscountPrice discountPrice )
    {
        DiscountPrice saveDiscountPrice = dP_repository.save(discountPrice);
        return saveDiscountPrice.getId();
    }

    public Long saveBasicPrice ( BasicPrice basicPrice )
    {
        BasicPrice saveBasicPrice = bP_repository.save(basicPrice);
        return saveBasicPrice.getId();
    }
    // Exception is missing
    public Optional<DiscountPrice> getOneDiscountPrice (Long id ){
        return dP_repository.findById(id);
    }
    // Exception is missing
    public Optional<BasicPrice> getOneBasicPrice (Long id ){
        return bP_repository.findById(id);
    }

    /*
    Eigentlich wollen wir ja auch den Zeitraum anpassen
    : setter für startTime & endTime notwendig?
     */
    public Long updateDiscountPrice ( DiscountPrice newDPrice, Long id ){
        return dP_repository.findById(id).map(discountPrice -> { discountPrice.setPrice(newDPrice.getPrice());
                                                            DiscountPrice saveDPrice = dP_repository.save(discountPrice);
                                                            return saveDPrice.getId();
        }).orElseThrow();
    }

    public Long updateBasicPrice ( BasicPrice newBPrice, Long id ){
        return bP_repository.findById(id).map(basicPrice ->  { basicPrice.setBasicPrice(newBPrice.getBasicPrice());
            BasicPrice saveBPrice = bP_repository.save(basicPrice);
            return saveBPrice.getId();
        }).orElseThrow();
    }

    /*
    Discount- & BasicPrice gehören zusammen & müssen zusammen entfernt werden
     */
    public void deleteDiscountAndBasicPrice (Long id){
        if ( dP_repository.findById(id).isEmpty() && bP_repository.findById(id).isEmpty()){
            // Exception is missing - hier könnte ihre Werbung stehen!
        }else{
            dP_repository.deleteById(id);
            bP_repository.deleteById(id);
        }
    }

}
