package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import lombok.Getter;
import lombok.ToString;

import javax.swing.plaf.IconUIResource;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@ToString
@Getter
public class SpecialOfferAbove300pln implements SpecialOffer {
    private final String name;

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

            if(product.getDiscountPrice() - fivePercentOfPrice < 0){
                product.setDiscountPrice(0);
                continue;
            }

            product.setDiscountPrice(product.getDiscountPrice() - fivePercentOfPrice);
        }
    }

    private boolean listOfProductsContainsFreeProducts(Product[] listOfProducts) {
        return Arrays.stream(listOfProducts)
                .filter(Objects::nonNull)
                .anyMatch(product -> product.getDiscountPrice() == 0);
    }

    @Override
    public boolean canApply(ListOfProducts listOfProducts) {
        //sum of real prices have to be bigger than 300
        if(listOfProducts.getSumPricesOfAllProducts() < 300)
            return false;

        //if some product is already free we can not use this special offer
        return !listOfProductsContainsFreeProducts(listOfProducts.getListOfProducts());
    }
}
