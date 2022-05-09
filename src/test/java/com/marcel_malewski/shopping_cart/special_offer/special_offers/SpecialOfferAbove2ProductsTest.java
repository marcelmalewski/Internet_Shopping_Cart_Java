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
        Product Product1 = new Product("1", "test", 12);
        Product Product2 = new Product("2", "test", 9);
        Product Product3 = new Product("3", "test", 10);

        this.basicListOfProducts.addProduct(Product1);
        this.basicListOfProducts.addProduct(Product2);
        this.basicListOfProducts.addProduct(Product3);

        this.specialOfferAbove2Products.apply(this.basicListOfProducts);

        //change price
        Product1.setDiscountPrice(12);
        Product2.setDiscountPrice(0);
        Product2.setDiscountPrice(10);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = Product1;
        expectedResult[1] = Product2;
        expectedResult[2] = Product3;

        assertArrayEquals(expectedResult , this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfSpecialOfferAbove2ProductsCanApplyReturnFalseWhenThereIsLessThan3NotFreeProducts() {
        Product testProduct1 = new Product("1", "test", 12);
        Product testProduct2 = new Product("2", "test", 9);
        Product testProduct3 = new Product("3", "test", 0);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);
        this.basicListOfProducts.addProduct(testProduct3);

        assertFalse(this.specialOfferAbove2Products.canApply(this.basicListOfProducts));
    }
}