package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Objects;

@ToString
@Getter
public class SpecialOfferAbove200WitFreeMug implements SpecialOffer {
    private final String name;

    public SpecialOfferAbove200WitFreeMug() {
        this.name = "specialOfferAbove200WitFreeMug";
    }

    @Override
    public void apply(ListOfProducts listOfProducts) {
        Product freeMug = new Product("4", "Mug", 0);
        listOfProducts.addProduct(freeMug);
    }

    private boolean checkIfThereIsFreeMugAlready(Product[] listOfProducts) {
        return Arrays.stream(listOfProducts)
                .filter(Objects::nonNull)
                .filter(product -> product.getName().equals("Mug"))
                .anyMatch(product -> product.getDiscountPrice() == 0);
    }

    @Override
    public boolean canApply(ListOfProducts listOfProducts) {
        if(listOfProducts.getSumPricesOfAllProducts() < 200)
            return false;

        return !checkIfThereIsFreeMugAlready(listOfProducts.getListOfProducts());
    }
}
