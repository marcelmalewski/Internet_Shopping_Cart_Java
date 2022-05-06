package com.marcel_malewski.lab3;

import com.marcel_malewski.lab3.list_of_products.ListOfProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ListOfProductsTest {
    ListOfProducts listOfProducts;
    Product product;

    @BeforeEach
    void init() {
        this.product = new Product("1", "test", 12.3);
        this.listOfProducts = new ListOfProducts();
    }

    @Test
    void testIfProductIsAddedToListOfProducts() {
        Product[] expectedResult = new Product[5];
        expectedResult[0] = this.product;

        this.listOfProducts.addElement(this.product);
        assertArrayEquals(expectedResult, this.listOfProducts.getListOfProducts());
    }

    @Test
    void testIfWeCanAddMoreThan5Products() {
        Product[] expectedResult = new Product[10];
        for(int i=0; i<=6; i++) {
            expectedResult[i] = this.product;
            this.listOfProducts.addElement(this.product);
        }

        assertArrayEquals(expectedResult, this.listOfProducts.getListOfProducts());
    }

    @Test
    void testIfProductIsRemovedFromListOfProducts() {
        Product[] expectedResult = new Product[5];

        this.listOfProducts.addElement(this.product);
        this.listOfProducts.removeElement(this.product);

        assertArrayEquals(expectedResult, this.listOfProducts.getListOfProducts());
    }

    @Test
    void testIfRemoveElementReturnsTrueWhenElementIsRemoved() {
        this.listOfProducts.addElement(this.product);

        assertTrue(this.listOfProducts.removeElement(this.product));
    }

    @Test
    void testIfRemoveElementReturnsFalseWhenElementIsNotRemoved() {
        assertFalse(this.listOfProducts.removeElement(this.product));
    }

    @Test
    void testIfAfterRemoveElementFromListWithSixElementsTheLengthOfListIsChanged() {
        Product tempProduct;

        for(int i=0; i<6; i++) {
            tempProduct = new Product(String.valueOf(i), "test", 12.3);
            this.listOfProducts.addElement(tempProduct);
        }

        tempProduct = new Product(String.valueOf(3), "test", 12.3);
        this.listOfProducts.removeElement(tempProduct);

        assertEquals(5, this.listOfProducts.getListOfProducts().length);
    }

    @Test
    void testIfPeekElementsWillReturnCorrectElements() {
        Product product1 = new Product("1", "testA", 12.3);
        Product product2 = new Product("2", "testB", 12.3);
        Product product3 = new Product("3", "testC", 12.3);

        this.listOfProducts.addElement(product1);
        this.listOfProducts.addElement(product2);
        this.listOfProducts.addElement(product3);

        Product[] expectedResult = new Product[2];
        expectedResult[0] = product1;
        expectedResult[1] = product2;

        assertArrayEquals(expectedResult, this.listOfProducts.peekElements(2));
    }

    @Test
    void testIfDiscountPricesAreSummedCorrect() {
        Product testProduct = new Product("1", "test", 3.1);
        testProduct.setDiscountPrice(2.1);

        for(int i=0; i<3; i++) {
            this.listOfProducts.addElement(testProduct);
        }

        double expectedResult = 6.3;

        assertEquals(expectedResult, this.listOfProducts.sumDiscountPricesOfAllProducts());
    }

    @Test
    void testIfPricesAreSummedCorrect() {
        Product testProduct = new Product("1", "test", 3.1);
        testProduct.setDiscountPrice(2.1);

        for(int i=0; i<3; i++) {
            this.listOfProducts.addElement(testProduct);
        }

        double expectedResult = 9.3;

        assertEquals(expectedResult, this.listOfProducts.sumPricesOfAllProducts());
    }

    @Test
    void testIfGetCheapestProductWillReturnProductAtAll() {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.listOfProducts.addElement(testProduct1);
        this.listOfProducts.addElement(testProduct2);

        assertTrue(this.listOfProducts.getCheapestProduct().isPresent());
    }

    @Test
    void testIfGetCheapestProductWillReturnCorrectProduct() {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.listOfProducts.addElement(testProduct1);
        this.listOfProducts.addElement(testProduct2);

        Optional<Product> expectedResult = Optional.of(testProduct2);

        assertEquals(expectedResult, this.listOfProducts.getCheapestProduct());
    }

    @Test
    void testIfGetExpensiveProductWillReturnProductAtAll() {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.listOfProducts.addElement(testProduct1);
        this.listOfProducts.addElement(testProduct2);

        assertTrue(this.listOfProducts.getMostExpensiveProduct().isPresent());
    }

    @Test
    void testIfGetExpensiveProductWillReturnCorrectProduct() {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.listOfProducts.addElement(testProduct1);
        this.listOfProducts.addElement(testProduct2);

        Optional<Product> ExpensiveProduct = Optional.of(testProduct1);

        assertEquals(ExpensiveProduct, this.listOfProducts.getMostExpensiveProduct());
    }
}