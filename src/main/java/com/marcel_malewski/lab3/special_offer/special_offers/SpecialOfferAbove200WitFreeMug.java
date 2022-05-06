package com.marcel_malewski.lab3.special_offer.special_offers;

import com.marcel_malewski.lab3.list_of_products.ListOfProducts;
import com.marcel_malewski.lab3.Product;

import java.util.Objects;

public class SpecialOfferAbove200WitFreeMug extends SpecialOffer {
    public SpecialOfferAbove200WitFreeMug(String name) {
        this.name = name;
    }

    @Override
    public void apply(ListOfProducts listOfProducts) {
        Product freeMug = new Product("4", "Mug", 0);
        listOfProducts.addElement(freeMug);
    }

    private boolean checkIfThereIsFreeMugAlready(Product[] listOfProducts) {
        for(Product product : listOfProducts) {
            //ignore nulls
            if(Objects.isNull(product))
                break;

            if(product.getName().equals("Mug") && product.getDiscountPrice() == 0)
                return true;

        }

        return false;
    }

    @Override
    public boolean canApply(ListOfProducts listOfProducts) {
        //sum of real prices should be higher than 200
        if(listOfProducts.sumPricesOfAllProducts() < 200)
            return false;

        //in list of products can not be free mug
        return !checkIfThereIsFreeMugAlready(listOfProducts.getListOfProducts());
    }
}
