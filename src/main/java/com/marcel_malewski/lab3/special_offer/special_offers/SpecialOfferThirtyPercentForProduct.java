package com.marcel_malewski.lab3.special_offer.special_offers;

import com.marcel_malewski.lab3.list_of_products.ListOfProducts;
import com.marcel_malewski.lab3.Product;

import java.util.Objects;

public class SpecialOfferThirtyPercentForProduct extends SpecialOffer {
    private final Product product;

    public SpecialOfferThirtyPercentForProduct(Product product, String name) {
        this.name = name;
        this.product = product;
    }

    @Override
    public void apply(ListOfProducts listOfProducts) {
        for (Product product : listOfProducts.getListOfProducts()) {
            if(Objects.isNull(product))
                break;

            //in case product already had some special offer
            //count 30 percent of original price
            if(product.getName().equals(this.product.getName())) {
                double thirtyPercentOfPrice = product.getPrice() * 0.30;

                if(product.getDiscountPrice() - thirtyPercentOfPrice < 0)
                    product.setDiscountPrice(0);
                else
                    product.setDiscountPrice(product.getDiscountPrice() - thirtyPercentOfPrice);
            }
        }
    }

    @Override
    public boolean canApply(ListOfProducts listOfProducts) {
        for (Product product : listOfProducts.getListOfProducts()) {
            if(Objects.isNull(product))
                break;
            //discount is for specific product so first this product has to be in list of products and can not be free
            if(product.getName().equals(this.product.getName()) && product.getDiscountPrice() > 0) {
                return true;
            }
        }

        return false;
    }
}
