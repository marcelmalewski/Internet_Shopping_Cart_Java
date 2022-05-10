package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.BasicListOfProducts;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialOfferAbove2ProductsTest {
    ListOfProducts basicListOfProducts;
    SpecialOfferAbove2Products specialOfferAbove2Products;

    @BeforeEach
    void init() {
        this.basicListOfProducts = new BasicListOfProducts();
        this.specialOfferAbove2Products = new SpecialOfferAbove2Products();
    }

    @Test
    void testIfSpecialOfferAbove2ProductsApplyWorksCorrect() throws Exception {
        Product product1 = new Product("1", "test", 12);
        Product product2 = new Product("2", "test", 9);
        Product product3 = new Product("3", "test", 10);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);
        this.basicListOfProducts.addProduct(product3);

        this.specialOfferAbove2Products.apply(this.basicListOfProducts);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = new Product("1", "test", 12);
        expectedResult[1] = new Product("2", "test", 9, 0);
        expectedResult[2] = new Product("3", "test", 10);

        assertArrayEquals(expectedResult , this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfSpecialOfferAbove2ProductsCanApplyReturnFalseWhenThereIsLessThan3NotFreeProducts() {
        Product product1 = new Product("1", "test", 12);
        Product product2 = new Product("2", "test", 9);
        Product product3 = new Product("3", "test", 0);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);
        this.basicListOfProducts.addProduct(product3);

        assertFalse(this.specialOfferAbove2Products.canApply(this.basicListOfProducts));
    }
}