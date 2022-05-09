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
    void testIfSpecialOfferThirtyPercentForProductApplyWorksCorrect() throws Exception {
        //Client
        Product Product1 = new Product("1", "test", 10);
        Product Product2 = new Product("2", "test", 10);

        this.basicListOfProducts.addProduct(Product1);
        this.basicListOfProducts.addProduct(Product2);

        SpecialOffer specialOfferThirtyPercentForProduct =
                new SpecialOfferThirtyPercentForProduct(Product1);
        specialOfferThirtyPercentForProduct.apply(this.basicListOfProducts);

        Product1.setDiscountPrice(7);
        Product2.setDiscountPrice(10);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = Product1;
        expectedResult[1] = Product2;

        assertArrayEquals(expectedResult , this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfSpecialOfferThirtyPercentForProductCanApplyReturnFalseWhenProductIsNotPresent() {
        Product Product1 = new Product("1", "test", 10);
        Product Product2 = new Product("2", "test", 10);
        Product Product3 = new Product("3", "test2", 10);

        this.basicListOfProducts.addProduct(Product1);
        this.basicListOfProducts.addProduct(Product2);

        this.specialOfferThirtyPercentForProduct =
                new SpecialOfferThirtyPercentForProduct(Product3);

        assertFalse(this.specialOfferThirtyPercentForProduct.canApply(this.basicListOfProducts));
    }

    @Test
    void testIfSpecialOfferThirtyPercentForProductCanApplyReturnFalseWhenProductIsFree() {
        Product Product1 = new Product("1", "test", 10);
        Product Product2 = new Product("2", "test2", 0);

        this.specialOfferThirtyPercentForProduct = new SpecialOfferThirtyPercentForProduct(Product2);

        this.basicListOfProducts.addProduct(Product1);
        this.basicListOfProducts.addProduct(Product2);

        assertFalse(this.specialOfferThirtyPercentForProduct.canApply(this.basicListOfProducts));
    }
}