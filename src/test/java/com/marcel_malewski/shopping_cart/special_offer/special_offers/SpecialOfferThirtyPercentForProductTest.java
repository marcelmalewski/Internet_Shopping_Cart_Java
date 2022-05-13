package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.BasicListOfProducts;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialOfferThirtyPercentForProductTest {
    SpecialOfferThirtyPercentForProduct specialOfferThirtyPercentForProduct;
    ListOfProducts basicListOfProducts;

    @BeforeEach
    void init() {
        this.basicListOfProducts = new BasicListOfProducts();
    }

    @Test
    void testIfSpecialOfferThirtyPercentForProductApplyWorksCorrect() {
        //Client
        Product product1 = new Product("1", "test", 10);
        Product product2 = new Product("2", "test", 10);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);

        this.specialOfferThirtyPercentForProduct =
                new SpecialOfferThirtyPercentForProduct(product1);

        specialOfferThirtyPercentForProduct.apply(this.basicListOfProducts);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = new Product("1", "test", 10, 7);
        expectedResult[1] = new Product("2", "test", 10);

        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfProductAfterPromotionShouldBeOnMinusIs0() {
        Product product1 = new Product("1", "test", 1000, 10);
        Product product2 = new Product("2", "test", 10);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);

        this.specialOfferThirtyPercentForProduct =
                new SpecialOfferThirtyPercentForProduct(product1);

        this.specialOfferThirtyPercentForProduct.apply(this.basicListOfProducts);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = new Product("1", "test", 1000, 0);
        expectedResult[1] = new Product("2", "test", 10);


        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfSpecialOfferThirtyPercentForProductCanApplyReturnFalseWhenProductIsNotPresent() {
        Product Product1 = new Product("1", "test", 10);
        Product product2 = new Product("2", "test", 10);
        Product product3 = new Product("3", "test2", 10);

        this.basicListOfProducts.addProduct(Product1);
        this.basicListOfProducts.addProduct(product2);

        this.specialOfferThirtyPercentForProduct =
                new SpecialOfferThirtyPercentForProduct(product3);

        assertFalse(this.specialOfferThirtyPercentForProduct.canApply(this.basicListOfProducts));
    }

    @Test
    void testIfSpecialOfferThirtyPercentForProductCanApplyReturnFalseWhenProductIsFree() {
        Product Product1 = new Product("1", "test", 10);
        Product product2 = new Product("2", "test2", 0);

        this.specialOfferThirtyPercentForProduct = new SpecialOfferThirtyPercentForProduct(product2);

        this.basicListOfProducts.addProduct(Product1);
        this.basicListOfProducts.addProduct(product2);

        assertFalse(this.specialOfferThirtyPercentForProduct.canApply(this.basicListOfProducts));
    }
}