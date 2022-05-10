package com.marcel_malewski.shopping_cart;

import com.marcel_malewski.shopping_cart.list_of_products.BasicListOfProducts;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import com.marcel_malewski.shopping_cart.list_of_products.sort.*;
import com.marcel_malewski.shopping_cart.special_offer.special_offer_orders.ApplySpecialOffer;
import com.marcel_malewski.shopping_cart.special_offer.special_offers.*;
import com.marcel_malewski.shopping_cart.special_offer.special_offer_orders.SpecialOfferOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    ShoppingCart shoppingCart;
    ListOfProducts basicListOfProducts;

    @BeforeEach
    void init() {
        this.basicListOfProducts = new BasicListOfProducts();
        this.shoppingCart = new ShoppingCart(this.basicListOfProducts);
    }

    @Test
    void testIfSortAscByPriceAndDescByPriceIsAddedByDefault() {
        SortProducts sortProductsAscByPrice = new SortProductsAscByPrice();
        SortProducts sortProductsDescByPrice = new SortProductsDescByPrice();

        HashSet<SortProducts> expectedResult = new HashSet<>();
        expectedResult.add(sortProductsAscByPrice);
        expectedResult.add(sortProductsDescByPrice);

        assertEquals(expectedResult, this.shoppingCart.getAvailableSortTypes());
    }

    @Test
    void testIfProductIsAddedToShoppingCart() throws Exception {
        Product product = new Product("1", "test", 12.3);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = product;

        this.shoppingCart.addProduct(product);
        assertArrayEquals(expectedResult, this.shoppingCart.getListOfProducts());
    }

    @Test
    void testIfProductIsRemovedFromShoppingCart() throws Exception {
        Product product = new Product("1", "test", 12.3);

        this.shoppingCart.addProduct(product);
        this.shoppingCart.removeProduct(product);

        Product[] expectedResult = new Product[5];

        assertArrayEquals(expectedResult, this.shoppingCart.getListOfProducts());
    }

    @Test
    void testIfRemoveProductReturnTrue() {
        Product product = new Product("1", "test", 12.3);
        this.shoppingCart.addProduct(product);

        assertTrue(this.shoppingCart.removeProduct(product));
    }

    @Test
    void testIfGetCheapestProductWorks() {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.shoppingCart.addProduct(testProduct1);
        this.shoppingCart.addProduct(testProduct2);

        Optional<Product> expectedResult = Optional.of(testProduct2);

        assertEquals(expectedResult, this.shoppingCart.getCheapestProduct());
    }

    @Test
    void testIfGetCheapestProductsWillReturnCorrectProducts() throws Exception {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 1.1);
        Product testProduct3 = new Product("3", "test", 2.1);

        this.shoppingCart.addProduct(testProduct1);
        this.shoppingCart.addProduct(testProduct2);
        this.shoppingCart.addProduct(testProduct3);

        Product[] expectedResult = {testProduct2, testProduct3};

        assertArrayEquals(expectedResult, this.shoppingCart.getCheapestProducts(2));
    }

    @Test
    void testIfGetMostExpensiveProductWorks() {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 2.1);

        this.shoppingCart.addProduct(testProduct1);
        this.shoppingCart.addProduct(testProduct2);

        Optional<Product> ExpensiveProduct = Optional.of(testProduct1);

        assertEquals(ExpensiveProduct, this.shoppingCart.getMostExpensiveProduct());
    }

    @Test
    void testIfGetMostExpensiveProductsWillReturnCorrectProducts() throws Exception {
        Product testProduct1 = new Product("1", "test", 3.1);
        Product testProduct2 = new Product("2", "test", 1.1);
        Product testProduct3 = new Product("3", "test", 2.1);

        this.shoppingCart.addProduct(testProduct1);
        this.shoppingCart.addProduct(testProduct2);
        this.shoppingCart.addProduct(testProduct3);

        Product[] expectedResult = {testProduct1, testProduct3};

        assertArrayEquals(expectedResult, this.shoppingCart.getMostExpensiveProducts(2));
    }

    @Test
    void testIfDefaultSortWorks() throws Exception {
        Product product1 = new Product("1", "testB", 11.3);
        Product product2 = new Product("2", "testA", 11.3);
        Product product3 = new Product("3", "test", 12.3);


        this.shoppingCart.addProduct(product1);
        this.shoppingCart.addProduct(product2);
        this.shoppingCart.addProduct(product3);

        this.shoppingCart.defaultSort();

        Product[] expectedResult = new Product[5];
        expectedResult[0] = product3;
        expectedResult[1] = product2;
        expectedResult[2] = product1;

        assertArrayEquals(expectedResult, this.shoppingCart.getListOfProducts());
    }

    @Test
    void testIfSortWorks() throws Exception {
        this.basicListOfProducts = new BasicListOfProducts();
        this.shoppingCart = new ShoppingCart(this.basicListOfProducts);

        Product product1 = new Product("1", "test", 12.3);
        Product product2 = new Product("2", "test", 11.3);

        this.shoppingCart.addProduct(product1);
        this.shoppingCart.addProduct(product2);
        this.shoppingCart.sort("ascByPrice");

        Product[] expectedResult = new Product[5];
        expectedResult[0] = product2;
        expectedResult[1] = product1;

        assertArrayEquals(expectedResult, this.shoppingCart.getListOfProducts());
    }

    @Test
    void testIfGetCurrentCostOfShoppingCartWorks() {
        Product product1 = new Product("1", "testB", 11.3, 10);
        Product product2 = new Product("2", "testA", 11.3, 9);
        Product product3 = new Product("3", "test", 12.3, 9);


        this.shoppingCart.addProduct(product1);
        this.shoppingCart.addProduct(product2);
        this.shoppingCart.addProduct(product3);

        double expectedResult = 28.0;

        assertEquals(expectedResult, this.shoppingCart.getCurrentCostOfShoppingCart());
    }

    @Test
    void testIfUsedSpecialOfferIsSaved() throws Exception {
        //Client
        //creating shopping cart with two products
        Product Product1 = new Product("1", "test", 1000);
        Product Product2 = new Product("2", "test", 10);

        //add products to shopping cart
        this.shoppingCart.addProduct(Product1);
        this.shoppingCart.addProduct(Product2);

        //create special offer order and special offer
        SpecialOffer specialOfferAbove300pln = new SpecialOfferAbove300pln();
        SpecialOfferOrder applySpecialOffer1 = new ApplySpecialOffer(specialOfferAbove300pln);

        //apply special offers orders
        this.shoppingCart.applySpecialOffers(applySpecialOffer1);

        String expectedResult = "specialOfferAbove300pln";

        assertEquals(expectedResult, this.shoppingCart.getCurrentSpecialOffers().get(0));
    }

    @Test
    void testIfTwoSameSpecialOffersCanNotBeSaved() throws Exception {
        //Client
        //creating shopping cart with two products
        Product Product1 = new Product("1", "test", 1000);
        Product Product2 = new Product("2", "test", 10);

        //add products to shopping cart
        this.shoppingCart.addProduct(Product1);
        this.shoppingCart.addProduct(Product2);

        //create special offer order and special offer
        SpecialOffer specialOfferAbove300pln = new SpecialOfferAbove300pln();
        SpecialOfferOrder applySpecialOffer1 = new ApplySpecialOffer(specialOfferAbove300pln);
        SpecialOfferOrder applySpecialOffer2 = new ApplySpecialOffer(specialOfferAbove300pln);

        //apply special offers orders
        this.shoppingCart.applySpecialOffers(applySpecialOffer1, applySpecialOffer2);

        int expectedResult = 1;


        assertEquals(expectedResult, this.shoppingCart.getCurrentSpecialOffers().size());
    }

    @Test
    void testIfSpecialOfferCanBeUsed() throws Exception {
        //Client
        Product product1 = new Product("1", "test", 1000);
        Product product2 = new Product("2", "test", 10);

        this.shoppingCart.addProduct(product1);
        this.shoppingCart.addProduct(product2);

        SpecialOfferAbove300pln specialOfferAbove300pln = new SpecialOfferAbove300pln();
        SpecialOfferOrder specialOfferOrder = new ApplySpecialOffer(specialOfferAbove300pln);

        this.shoppingCart.applySpecialOffers(specialOfferOrder);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = new Product("1", "test", 1000, 950);
        expectedResult[1] = new Product("2", "test", 10, 9.5);

        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfTwoSpecialOffersCanBeUsed() throws Exception {
        //Client
        Product product1 = new Product("1", "test", 1000);
        Product product2 = new Product("2", "test", 10);

        this.shoppingCart.addProduct(product1);
        this.shoppingCart.addProduct(product2);

        SpecialOfferAbove300pln specialOfferAbove300pln = new SpecialOfferAbove300pln();
        SpecialOfferThirtyPercentForProduct specialOfferThirtyPercentForProduct = new SpecialOfferThirtyPercentForProduct(product1);

        SpecialOfferOrder specialOfferOrder1 = new ApplySpecialOffer(specialOfferAbove300pln);
        SpecialOfferOrder specialOfferOrder2 = new ApplySpecialOffer(specialOfferThirtyPercentForProduct);

        this.shoppingCart.applySpecialOffers(specialOfferOrder1, specialOfferOrder2);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = new Product("1", "test", 1000, 650);
        expectedResult[1] = new Product("2", "test", 10, 9.5);

        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfGetListOfProductsWorks() {
        //Client
        Product product1 = new Product("1", "test", 1000);
        Product product2 = new Product("2", "test", 10);

        this.shoppingCart.addProduct(product1);
        this.shoppingCart.addProduct(product2);

        Product[] expectedResult = new Product[5];
        expectedResult[0] = new Product("1", "test", 1000);
        expectedResult[1] = new Product("2", "test", 10);

        assertArrayEquals(expectedResult, this.basicListOfProducts.getListOfProducts());
    }

    @Test
    void testIfAddAndGetSortTypeWorks() {
        SortProducts sortProductsAscByPrice = new SortProductsAscByPrice();
        SortProducts sortProductsDescByPrice = new SortProductsDescByPrice();
        SortProducts sortProductsDescByName = new SortProductsDescByName();

        this.shoppingCart.addNewSortType(sortProductsDescByName);

        HashSet<SortProducts> expectedResult = new HashSet<>();
        expectedResult.add(sortProductsAscByPrice);
        expectedResult.add(sortProductsDescByPrice);
        expectedResult.add(sortProductsDescByName);

        assertEquals(expectedResult, this.basicListOfProducts.getAvailableSortTypes());
    }
}