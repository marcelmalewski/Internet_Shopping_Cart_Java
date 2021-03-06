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
        listOfProducts.setListOfProducts(
                Arrays.stream(listOfProducts.getListOfProducts())
                        .map(product -> {
                            if(Objects.isNull(product))
                                return null;

                            //in case some product already had some special offer
                            //count 5 percent of original price
                            double fivePercentOfPrice = product.getPrice() * 0.05;

                            if(product.getDiscountPrice() - fivePercentOfPrice < 0){
                                product.setDiscountPrice(0);
                                return product;
                            }

                            product.setDiscountPrice(product.getDiscountPrice() - fivePercentOfPrice);
                            return product;
                        })
                        .toArray(Product[]::new)
        );
    }

    private boolean listOfProductsContainsFreeProducts(Product[] listOfProducts) {
        return Arrays.stream(listOfProducts)
                .filter(Objects::nonNull)
                .anyMatch(product -> product.getDiscountPrice() == 0);
    }

    @Override
    public boolean canApply(ListOfProducts listOfProducts) {
        if(listOfProducts.getSumPricesOfAllProducts() < 300)
            return false;

        return !listOfProductsContainsFreeProducts(listOfProducts.getListOfProducts());
    }
}
