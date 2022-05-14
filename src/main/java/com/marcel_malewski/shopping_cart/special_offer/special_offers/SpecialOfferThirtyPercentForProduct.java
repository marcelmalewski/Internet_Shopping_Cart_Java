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
        final boolean[] promotionApplied = {false};

        listOfProducts.setListOfProducts(
                Arrays.stream(listOfProducts.getListOfProducts())
                        .map(product -> {
                            if(Objects.isNull(product))
                                return null;

                            if(promotionApplied[0])
                                return product;

                            if(!product.getName().equals(this.product.getName()))
                                return product;

                            promotionApplied[0] = true;

                            //in case product already had some special offer
                            //count 30 percent of original price
                            double thirtyPercentOfPrice = product.getPrice() * 0.30;

                            if(product.getDiscountPrice() - thirtyPercentOfPrice < 0){
                                product.setDiscountPrice(0);
                                return product;
                            }

                            product.setDiscountPrice(product.getDiscountPrice() - thirtyPercentOfPrice);
                            return product;
                        })
                        .toArray(Product[]::new)
        );
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
