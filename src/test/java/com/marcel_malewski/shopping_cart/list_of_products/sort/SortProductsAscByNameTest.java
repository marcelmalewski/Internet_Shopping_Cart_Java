package com.marcel_malewski.shopping_cart.list_of_products.sort;

import com.marcel_malewski.shopping_cart.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortProductsAscByNameTest {

    @Test
    void testIfSortWorksCorrect() {
        Product product1 = new Product("1", "testB", 12.3);
        Product product2 = new Product("2", "testA", 12.3);

        Product[] listOfProducts = new Product[]{product1, product2};
        SortProducts sortProductsAscByName = new SortProductsAscByName();

        sortProductsAscByName.sort(listOfProducts);

        Product[] expectedResult = new Product[2];
        expectedResult[0] = product2;
        expectedResult[1] = product1;

        assertArrayEquals(expectedResult, listOfProducts);
    }
}