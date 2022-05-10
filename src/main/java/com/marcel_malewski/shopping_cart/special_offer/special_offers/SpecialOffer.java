package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;

//interface of receiver
public interface SpecialOffer {
    String getName();
    void apply(ListOfProducts listOfProducts) throws Exception;
    boolean canApply(ListOfProducts listOfProducts);
}
