package com.marcel_malewski.shopping_cart.list_of_products;

import com.marcel_malewski.shopping_cart.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortProductsTest {
    @Test
    void defaultSort() {
        Product product1 = new Product("1", "testB", 11.3);
        Product product2 = new Product("2", "testA", 11.3);
        Product product3 = new Product("3", "test", 12.3);

        Product[] listOfProducts = new Product[]{product1, product2, product3};
        SortProducts sortProducts = new SortProducts(listOfProducts);

        sortProducts.defaultSort();

        Product[] expectedResult = new Product[3];
        expectedResult[0] = product3;
        expectedResult[1] = product2;
        expectedResult[2] = product1;

        assertArrayEquals(expectedResult, listOfProducts);
    }

    @Test
    void sortProductsAscByPrice() {
        Product product1 = new Product("1", "test", 12.3);
        Product product2 = new Product("2", "test", 11.3);

        Product[] listOfProducts = new Product[]{product1, product2};
        SortProducts sortProducts = new SortProducts(listOfProducts);

        sortProducts.sortProductsAscByPrice();

        Product[] expectedResult = new Product[2];
        expectedResult[0] = product2;
        expectedResult[1] = product1;

        assertArrayEquals(expectedResult, listOfProducts);
    }

    @Test
    void sortProductsDescByPrice() {
        Product product1 = new Product("1", "test", 11.3);
        Product product2 = new Product("2", "test", 12.3);

        Product[] listOfProducts = new Product[]{product1, product2};
        SortProducts sortProducts = new SortProducts(listOfProducts);

        sortProducts.sortProductsDescByPrice();

        Product[] expectedResult = new Product[2];
        expectedResult[0] = product2;
        expectedResult[1] = product1;

        assertArrayEquals(expectedResult, listOfProducts);
    }

    @Test
    void sortProductsAscByName() {
        Product product1 = new Product("1", "testB", 12.3);
        Product product2 = new Product("2", "testA", 12.3);

        Product[] listOfProducts = new Product[]{product1, product2};
        SortProducts sortProducts = new SortProducts(listOfProducts);

        sortProducts.sortProductsAscByName();

        Product[] expectedResult = new Product[2];
        expectedResult[0] = product2;
        expectedResult[1] = product1;

        assertArrayEquals(expectedResult, listOfProducts);
    }

    @Test
    void sortProductsDescByName() {
        Product product1 = new Product("1", "testA", 12.3);
        Product product2 = new Product("2", "testB", 12.3);

        Product[] listOfProducts = new Product[]{product1, product2};
        SortProducts sortProducts = new SortProducts(listOfProducts);

        sortProducts.sortProductsDescByName();

        Product[] expectedResult = new Product[2];
        expectedResult[0] = product2;
        expectedResult[1] = product1;

        assertArrayEquals(expectedResult, listOfProducts);
    }
}