package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.BasicListOfProducts;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialOfferAbove200WitFreeMugTest {
    SpecialOfferAbove200WitFreeMug specialOfferAbove200WitFreeMug;
    ListOfProducts basicListOfProducts;

    @BeforeEach
    void init() {
        specialOfferAbove200WitFreeMug = new SpecialOfferAbove200WitFreeMug();
        basicListOfProducts = new BasicListOfProducts();
    }

    @Test
    void testIfSpecialOfferAbove200WithFreeMugApplyWorksCorrect() {
        Product product1 = new Product("1", "test", 200);
        this.basicListOfProducts.addProduct(product1);

        this.specialOfferAbove200WitFreeMug.apply(this.basicListOfProducts);

        Product testProductMug = new Product("4", "Mug", 0);

        assertEquals(testProductMug, this.basicListOfProducts.getListOfProducts()[1]);
    }

    @Test
    void testIfSpecialOfferAbove200WithFreeMugCanApplyReturnsFalseWhenThereIsFreeMug() {
        Product product1 = new Product("1", "test", 200);
        Product product2 = new Product("2", "Mug", 0);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);

        assertFalse(this.specialOfferAbove200WitFreeMug.canApply(this.basicListOfProducts));
    }

    @Test
    void testIfSpecialOfferAbove200WithFreeMugCanApplyReturnsFalseWhenLessThan200pln() {
        Product product1 = new Product("1", "test", 100);
        this.basicListOfProducts.addProduct(product1);

        assertFalse(this.specialOfferAbove200WitFreeMug.canApply(this.basicListOfProducts));
    }
}