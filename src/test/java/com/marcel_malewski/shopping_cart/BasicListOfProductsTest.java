package com.marcel_malewski.shopping_cart;

import com.marcel_malewski.shopping_cart.list_of_products.BasicListOfProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BasicListOfProductsTest {
    BasicListOfProducts basicListOfProducts;
    Product product;

    @BeforeEach
    void init() {
        this.product = new Product("1", "test", 12.3);
        this.basicListOfProducts = new BasicListOfProducts();
    }

    @Test
    void testIfProductIsAddedToListOfProducts() {
        Product[] expectedResult = new Product[5];
        expectedResult[0] = this.product;

        this.basicListOfProducts.addProduct(this.product);
        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfWeCanAddMoreThan5Products() {
        Product[] expectedResult = new Product[10];
        for(int i=0; i<=6; i++) {
            expectedResult[i] = this.product;
            this.basicListOfProducts.addProduct(this.product);
        }

        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfProductIsRemovedFromListOfProducts() {
        Product[] expectedResult = new Product[5];

        this.basicListOfProducts.addProduct(this.product);
        this.basicListOfProducts.removeProduct(this.product);

        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfRemoveElementReturnsTrueWhenElementIsRemoved() {
        this.basicListOfProducts.addProduct(this.product);

        assertTrue(this.basicListOfProducts.removeProduct(this.product));
    }

    @Test
    void testIfRemoveElementReturnsFalseWhenElementIsNotRemoved() {
        assertFalse(this.basicListOfProducts.removeProduct(this.product));
    }

    @Test
    void testIfAfterRemoveElementFromListWithSixElementsTheLengthOfListIsChanged() {
        Product tempProduct;

        for(int i=0; i<6; i++) {
            tempProduct = new Product(String.valueOf(i), "test", 12.3);
            this.basicListOfProducts.addProduct(tempProduct);
        }

        tempProduct = new Product(String.valueOf(3), "test", 12.3);
        this.basicListOfProducts.removeProduct(tempProduct);

        assertEquals(5, this.basicListOfProducts.getListOfProducts().length);
    }

    @Test
    void testIfPeekElementsWillReturnCorrectElements() {
        Product product1 = new Product("1", "testA", 12.3);
        Product product2 = new Product("2", "testB", 12.3);
        Product product3 = new Product("3", "testC", 12.3);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);
        this.basicListOfProducts.addProduct(product3);

        Product[] expectedResult = new Product[2];
        expectedResult[0] = product1;
        expectedResult[1] = product2;

        assertArrayEquals(expectedResult, this.basicListOfProducts.getProducts(2));
    }

    @Test
    void testIfDiscountPricesAreSummedCorrect() {
        Product testProduct = new Product("1", "test", 3.1);
        testProduct.setDiscountPrice(2.1);

        for(int i=0; i<3; i++) {
            this.basicListOfProducts.addProduct(testProduct);
        }

        double expectedResult = 6.3;

        assertEquals(expectedResult, this.basicListOfProducts.getSumDiscountPricesOfAllProducts());
    }

    @Test
    void testIfPricesAreSummedCorrect() {
        Product testProduct = new Product("1", "test", 3.1);
        testProduct.setDiscountPrice(2.1);

        for(int i=0; i<3; i++) {
            this.basicListOfProducts.addProduct(testProduct);
        }

        double expectedResult = 9.3;

        assertEquals(expectedResult, this.basicListOfProducts.getSumPricesOfAllProducts());
    }

    @Test
    void testIfGetCheapestProductWillReturnProductAtAll() {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);

        assertTrue(this.basicListOfProducts.getCheapestProduct().isPresent());
    }

    @Test
    void testIfGetCheapestProductWillReturnCorrectProduct() {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);

        Optional<Product> expectedResult = Optional.of(testProduct2);

        assertEquals(expectedResult, this.basicListOfProducts.getCheapestProduct());
    }

    @Test
    void testIfGetExpensiveProductWillReturnProductAtAll() {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);

        assertTrue(this.basicListOfProducts.getMostExpensiveProduct().isPresent());
    }

    @Test
    void testIfGetExpensiveProductWillReturnCorrectProduct() {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);

        Optional<Product> ExpensiveProduct = Optional.of(testProduct1);

        assertEquals(ExpensiveProduct, this.basicListOfProducts.getMostExpensiveProduct());
    }
}