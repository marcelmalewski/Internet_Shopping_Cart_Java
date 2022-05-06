package com.marcel_malewski.shopping_cart.special_offer.special_offer_orders;

import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import com.marcel_malewski.shopping_cart.special_offer.special_offers.SpecialOffer;

//interface of ConcreteCommand
public interface SpecialOfferOrder {
    boolean execute(ListOfProducts listOfProducts) throws Exception;
    SpecialOffer specialOffer();
}
