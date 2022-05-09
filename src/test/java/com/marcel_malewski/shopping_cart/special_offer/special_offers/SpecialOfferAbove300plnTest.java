package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.BasicListOfProducts;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import com.marcel_malewski.shopping_cart.special_offer.special_offer_orders.ApplySpecialOffer;
import com.marcel_malewski.shopping_cart.special_offer.special_offer_orders.SpecialOfferOrder;
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
        Product testProduct1 = new Product("1", "test", 1000);
        Product testProduct2 = new Product("2", "test", 10);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);

        this.specialOfferAbove300pln.apply(this.basicListOfProducts);

        testProduct1.setDiscountPrice(950);
        testProduct2.setDiscountPrice(9.5);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = testProduct1;
        expectedResult[1] = testProduct2;

        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfSpecialOfferAbove300plnCanApplyReturnFalseWhenThereIsFreeProduct() {
        Product testProduct1 = new Product("1", "test", 10);
        Product testProduct2 = new Product("2", "test", 0);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);

        assertFalse(this.specialOfferAbove300pln.canApply(this.basicListOfProducts));
    }

    @Test
    void testIfSpecialOfferAbove300plnCanApplyReturnFalseWhenCostIsLessThan300() {
        Product testProduct1 = new Product("1", "test", 10);
        Product testProduct2 = new Product("2", "test", 10);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);

        assertFalse(this.specialOfferAbove300pln.canApply(this.basicListOfProducts));
    }
}