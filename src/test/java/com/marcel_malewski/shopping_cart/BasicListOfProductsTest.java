package com.marcel_malewski.shopping_cart;

import com.marcel_malewski.shopping_cart.list_of_products.BasicListOfProducts;
import com.marcel_malewski.shopping_cart.list_of_products.sort.SortProducts;
import com.marcel_malewski.shopping_cart.list_of_products.sort.SortProductsAscByPrice;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BasicListOfProductsTest {
    BasicListOfProducts basicListOfProducts;
    @Test
    void testIfProductIsAddedToListOfProducts() {
        this.basicListOfProducts = new BasicListOfProducts();

        Product product = new Product("1", "test", 12.3);
        Product[] expectedResult = new Product[5];
        expectedResult[0] = product;

        this.basicListOfProducts.addProduct(product);
        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfWeCanAddMoreThan5Products() {
        this.basicListOfProducts = new BasicListOfProducts();

        Product product = new Product("1", "test", 12.3);
        Product[] expectedResult = new Product[10];
        for(int i=0; i<=6; i++) {
            expectedResult[i] = product;
            this.basicListOfProducts.addProduct(product);
        }

        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfProductIsRemovedFromListOfProducts() {
        this.basicListOfProducts = new BasicListOfProducts();

        Product product = new Product("1", "test", 12.3);
        Product[] expectedResult = new Product[5];

        this.basicListOfProducts.addProduct(product);
        this.basicListOfProducts.removeProduct(product);

        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfRemoveProductReturnsTrueWhenProductIsRemoved() {
        this.basicListOfProducts = new BasicListOfProducts();

        Product product = new Product("1", "test", 12.3);
        this.basicListOfProducts.addProduct(product);

        assertTrue(this.basicListOfProducts.removeProduct(product));
    }

    @Test
    void testIfRemoveProductReturnsFalseWhenProductIsNotRemoved() {
        this.basicListOfProducts = new BasicListOfProducts();

        Product product = new Product("1", "test", 12.3);

        assertFalse(this.basicListOfProducts.removeProduct(product));
    }

    @Test
    void testIfAfterRemoveProductFromListWithSixProductsTheLengthOfListIsChanged() {
        this.basicListOfProducts = new BasicListOfProducts();

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
    void testIfGetProductsWillReturnCorrectProduct() {
        this.basicListOfProducts = new BasicListOfProducts();

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
    void testIfGet6ProductsWhenWeHave2Works() {
        this.basicListOfProducts = new BasicListOfProducts();

        Product product1 = new Product("1", "testA", 12.3);
        Product product2 = new Product("2", "testB", 12.3);
        Product product3 = new Product("3", "testC", 12.3);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);
        this.basicListOfProducts.addProduct(product3);

        Product[] expectedResult = new Product[6];
        expectedResult[0] = product1;
        expectedResult[1] = product2;
        expectedResult[2] = product3;

        assertArrayEquals(expectedResult, this.basicListOfProducts.getProducts(6));
    }

    @Test
    void testIfDiscountPricesAreSummedCorrect() {
        this.basicListOfProducts = new BasicListOfProducts();

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
        this.basicListOfProducts = new BasicListOfProducts();

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
        this.basicListOfProducts = new BasicListOfProducts();

        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);

        assertTrue(this.basicListOfProducts.getCheapestProduct().isPresent());
    }

    @Test
    void testIfGetCheapestProductWillReturnCorrectProduct() {
        this.basicListOfProducts = new BasicListOfProducts();

        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);

        Optional<Product> expectedResult = Optional.of(testProduct2);

        assertEquals(expectedResult, this.basicListOfProducts.getCheapestProduct());
    }

    @Test
    void testIfGetExpensiveProductWillReturnProductAtAll() {
        this.basicListOfProducts = new BasicListOfProducts();

        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);

        assertTrue(this.basicListOfProducts.getMostExpensiveProduct().isPresent());
    }

    @Test
    void testIfGetExpensiveProductWillReturnCorrectProduct() {
        this.basicListOfProducts = new BasicListOfProducts();

        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.basicListOfProducts.addProduct(testProduct1);
        this.basicListOfProducts.addProduct(testProduct2);

        Optional<Product> ExpensiveProduct = Optional.of(testProduct1);

        assertEquals(ExpensiveProduct, this.basicListOfProducts.getMostExpensiveProduct());
    }

    @Test
    void testIfDefaultSortWorksCorrect() {
        this.basicListOfProducts = new BasicListOfProducts();

        Product product1 = new Product("1", "testB", 11.3);
        Product product2 = new Product("2", "testA", 11.3);
        Product product3 = new Product("3", "test", 12.3);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);
        this.basicListOfProducts.addProduct(product3);

        this.basicListOfProducts.defaultSort();

        Product[] expectedResult = new Product[5];
        expectedResult[0] = product3;
        expectedResult[1] = product2;
        expectedResult[2] = product1;

        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfAddSortTypeWorks() {
        SortProducts sortProductsAscByPrice = new SortProductsAscByPrice();

        this.basicListOfProducts = new BasicListOfProducts();
        this.basicListOfProducts.addSortTypes(sortProductsAscByPrice);

        HashSet<SortProducts> expectedResult = new HashSet<>();
        expectedResult.add(sortProductsAscByPrice);

        assertEquals(expectedResult, this.basicListOfProducts.getAvailableSortTypes());
    }
    @Test
    void testIfAddedSortTypeWorks() throws Exception {
        SortProducts sortProductsAscByPrice = new SortProductsAscByPrice();

        this.basicListOfProducts = new BasicListOfProducts(sortProductsAscByPrice);

        Product product1 = new Product("1", "test", 12.3);
        Product product2 = new Product("2", "test", 11.3);

        this.basicListOfProducts.addProduct(product1);
        this.basicListOfProducts.addProduct(product2);

        this.basicListOfProducts.sort("ascByPrice");

        Product[] expectedResult = new Product[5];
        expectedResult[0] = product2;
        expectedResult[1] = product1;

        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }
}