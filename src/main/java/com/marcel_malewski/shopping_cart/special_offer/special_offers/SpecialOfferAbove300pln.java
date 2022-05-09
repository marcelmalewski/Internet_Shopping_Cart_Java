package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;

import java.util.Objects;

public class SpecialOfferAbove300pln extends SpecialOffer {

    public SpecialOfferAbove300pln() {
        this.name = "specialOfferAbove300pln";
    }

    @Override
    public void apply(ListOfProducts listOfProducts) {
        for (Product product : listOfProducts.getListOfProducts()) {
            if(Objects.isNull(product))
                break;
            //in case some product already had some special offer
            //count 5 percent of original price
            double fivePercentOfPrice = product.getPrice() * 0.05;
            if(product.getDiscountPrice() - fivePercentOfPrice < 0)
                product.setDiscountPrice(0);
            else
                product.setDiscountPrice(product.getDiscountPrice() - fivePercentOfPrice);
        }
    }

    @Override
    public boolean canApply(ListOfProducts listOfProducts) {
        //sum of real prices have to be bigger than 300
        if(listOfProducts.getSumPricesOfAllProducts() < 300)
            return false;
        //is some product is already free we can not use this special offer
        for (Product product : listOfProducts.getListOfProducts()) {
            //ignore nulls
            if(Objects.isNull(product))
                break;

            if(product.getDiscountPrice() == 0) {
                return false;
            }
        }

        return true;
    }
}
