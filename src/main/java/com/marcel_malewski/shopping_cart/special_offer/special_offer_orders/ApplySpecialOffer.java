package com.marcel_malewski.shopping_cart.special_offer.special_offer_orders;

import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import com.marcel_malewski.shopping_cart.special_offer.special_offers.SpecialOffer;
import lombok.Getter;
import lombok.ToString;

//concrete Command
@Getter
@ToString
public final class ApplySpecialOffer implements SpecialOfferOrder {
    private final SpecialOffer specialOffer;

    public ApplySpecialOffer(SpecialOffer specialOffer) {
        this.specialOffer = specialOffer;
    }

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
