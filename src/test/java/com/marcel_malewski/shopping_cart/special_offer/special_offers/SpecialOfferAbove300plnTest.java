package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.BasicListOfProducts;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialOfferAbove300plnTest {
    SpecialOfferAbove300pln specialOfferAbove300pln;
    ListOfProducts basicListOfProducts;

    @BeforeEach
    void init() {
        this.specialOfferAbove300pln = new SpecialOfferAbove300pln();
        this.basicListOfProducts = new BasicListOfProducts();
    }

    @Test
    void testIfSpecialOfferAbove300plnApplyWorksCorrect() {
        Product product1 = new Product("1", "test", 1000);
        Product product2 = new Product("2", "test", 10);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);

        this.specialOfferAbove300pln.apply(this.basicListOfProducts);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = new Product("1", "test", 1000, 950);
        expectedResult[1] = new Product("2", "test", 10, 9.5);


        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfProductAfterPromotionShouldBeOnMinusIs0() {
        Product product1 = new Product("1", "test", 1000, 10);
        Product product2 = new Product("2", "test", 10);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);

        this.specialOfferAbove300pln.apply(this.basicListOfProducts);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = new Product("1", "test", 1000, 0);
        expectedResult[1] = new Product("2", "test", 10, 9.5);


        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfSpecialOfferAbove300plnCanApplyReturnFalseWhenThereIsFreeProduct() {
        Product product1 = new Product("1", "test", 10);
        Product product2 = new Product("2", "test", 0);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);

        assertFalse(this.specialOfferAbove300pln.canApply(this.basicListOfProducts));
    }

    @Test
    void testIfSpecialOfferAbove300plnCanApplyReturnFalseWhenCostIsLessThan300() {
        Product product1 = new Product("1", "test", 10);
        Product product2 = new Product("2", "test", 10);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);

        assertFalse(this.specialOfferAbove300pln.canApply(this.basicListOfProducts));
    }
}