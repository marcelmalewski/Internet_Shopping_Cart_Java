package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import com.marcel_malewski.shopping_cart.Product;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Objects;

@ToString
@Getter
public class SpecialOfferThirtyPercentForProduct implements SpecialOffer {
    private final String name;
    private final Product product;

    public SpecialOfferThirtyPercentForProduct(Product product) {
        this.name = "specialOfferThirtyPercentForProduct";
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

                if(product.getDiscountPrice() - thirtyPercentOfPrice < 0){
                    product.setDiscountPrice(0);
                    //discount only one product
                    break;
                }

                product.setDiscountPrice(product.getDiscountPrice() - thirtyPercentOfPrice);
                //discount only one product
                break;
            }
        }
    }

    private boolean listOfProductsContainsProductAndProductIsNotFree(Product[] listOfProducts) {
        return Arrays.stream(listOfProducts)
                .filter(Objects::nonNull)
                .filter(product -> product.getName().equals(this.product.getName()))
                .anyMatch(product -> product.getDiscountPrice() > 0);
    }

    @Override
    public boolean canApply(ListOfProducts listOfProducts) {
        return listOfProductsContainsProductAndProductIsNotFree(listOfProducts.getListOfProducts());
    }
}
