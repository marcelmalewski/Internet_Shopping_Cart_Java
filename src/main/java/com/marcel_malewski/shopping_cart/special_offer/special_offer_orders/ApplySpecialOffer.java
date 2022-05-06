package com.marcel_malewski.shopping_cart.special_offer.special_offer_orders;

import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import com.marcel_malewski.shopping_cart.special_offer.special_offers.SpecialOffer;
import lombok.Getter;

//concrete Command
@Getter
public class ApplySpecialOffer implements SpecialOfferOrder {
    private final SpecialOffer specialOffer;

    public ApplySpecialOffer(SpecialOffer specialOffer) {
        this.specialOffer = specialOffer;
    }

    @Override
    public boolean execute(ListOfProducts listOfProducts) throws Exception {
        if(specialOffer.canApply(listOfProducts)) {
            specialOffer.apply(listOfProducts);
            return true;
        } else {
            return false;
        }
    }
}
