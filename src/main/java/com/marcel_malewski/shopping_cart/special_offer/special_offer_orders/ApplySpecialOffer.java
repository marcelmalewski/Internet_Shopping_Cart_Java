package com.marcel_malewski.shopping_cart.special_offer.special_offer_orders;

import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import com.marcel_malewski.shopping_cart.special_offer.special_offers.SpecialOffer;

//concrete Command
public record ApplySpecialOffer(SpecialOffer specialOffer) implements SpecialOfferOrder {
    @Override
    public boolean execute(ListOfProducts listOfProducts) throws Exception {
        if (this.specialOffer.canApply(listOfProducts)) {
            this.specialOffer.apply(listOfProducts);
            return true;
        } else {
            return false;
        }
    }
}
