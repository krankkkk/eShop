package de.baleipzig.prices;

import de.baleipzig.prices.persistance.basicPrice.BasicPrice;
import de.baleipzig.prices.persistance.basicPrice.BasicPriceRepository;
import de.baleipzig.prices.persistance.discountPrice.DiscountPrice;
import de.baleipzig.prices.persistance.discountPrice.DiscountPriceRepository;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    private final DiscountPriceRepository dpRepository;
    private final BasicPriceRepository bpRepository;


    public PriceService(DiscountPriceRepository dpRepository, BasicPriceRepository bpRepository) {
        this.dpRepository = dpRepository;
        this.bpRepository = bpRepository;
    }

    private Long saveDiscountPrice ( DiscountPrice discountPrice )
    {
        DiscountPrice saveDiscountPrice = dpRepository.save(discountPrice);
        return saveDiscountPrice.getId();
    }

    private Long saveBasicPrice ( BasicPrice basicPrice )
    {
        BasicPrice saveBasicPrice = bpRepository.save(basicPrice);
        return saveBasicPrice.getId();
    }

    private DiscountPrice getOneDiscountPrice (Long id ){

        return dpRepository.findById(id).orElseThrow(()-> new PriceIdNotFoundException(id));
    }

    private BasicPrice getOneBasicPrice (Long id ){
        return bpRepository.findById(id).orElseThrow(()-> new PriceIdNotFoundException(id));
    }

    private void deleteDiscountPrice ( Long id ) {

        if ( dpRepository.findById(id).isEmpty() ){
            dpRepository.deleteById(id);
        }else {
            throw new PriceIdNotFoundException(id);
        }
    }

    private void deleteBasicPrice ( Long id ){

        if ( bpRepository.findById(id).isEmpty() ){
            bpRepository.deleteById(id);
        }else {
            throw new PriceIdNotFoundException(id);
        }
    }

    private void deleteDiscountAndBasicPrice ( Long id ){

        if ( bpRepository.findById(id).isEmpty() ){

            if ( dpRepository.findById(id).isEmpty() ){
                dpRepository.deleteById(id);
                bpRepository.deleteById(id);
            }else {
               throw new PriceIdNotFoundException(id);
            }
        }
        else{
            throw new PriceIdNotFoundException(id);
        }

    }

}
