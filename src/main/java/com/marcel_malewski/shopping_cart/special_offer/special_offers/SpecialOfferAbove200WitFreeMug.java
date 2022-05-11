package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import lombok.Getter;
import lombok.ToString;

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
        if(listOfProducts.getSumPricesOfAllProducts() < 200)
            return false;

        //in list of products can not be free mug
        return !checkIfThereIsFreeMugAlready(listOfProducts.getListOfProducts());
    }
}
