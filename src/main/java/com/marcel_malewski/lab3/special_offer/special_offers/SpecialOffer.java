package com.marcel_malewski.lab3.special_offer.special_offers;

import com.marcel_malewski.lab3.list_of_products.ListOfProducts;

//interface of receiver
public abstract class SpecialOffer {
    String name;
    public String getName() {
        return this.name;
    }

    abstract public void apply(ListOfProducts listOfProducts) throws Exception;

    abstract public boolean canApply(ListOfProducts listOfProducts);
}
