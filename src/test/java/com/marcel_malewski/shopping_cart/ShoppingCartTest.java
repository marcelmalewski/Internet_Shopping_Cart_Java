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
    void testIfSortAscByPriceAndDescByPriceIsAddedByDefault() {
        SortProducts sortProductsAscByPrice = new SortProductsAscByPrice();
        SortProducts sortProductsDescByPrice = new SortProductsDescByPrice();

        HashSet<SortProducts> expectedResult = new HashSet<>();
        expectedResult.add(sortProductsAscByPrice);
        expectedResult.add(sortProductsDescByPrice);

        assertEquals(expectedResult, this.shoppingCart.getAvailableSortTypes());
    }

    @Test
    void testIfAddingNewSortTypesWorks() {
        SortProducts sortProductsAscByName = new SortProductsAscByName();

        this.basicListOfProducts = new BasicListOfProducts(sortProductsAscByName);
        this.shoppingCart = new ShoppingCart(this.basicListOfProducts);

        SortProducts sortProductsAscByPrice = new SortProductsAscByPrice();
        SortProducts sortProductsDescByPrice = new SortProductsDescByPrice();

        HashSet<SortProducts> expectedResult = new HashSet<>();
        expectedResult.add(sortProductsAscByPrice);
        expectedResult.add(sortProductsDescByPrice);
        expectedResult.add(sortProductsAscByName);

        assertEquals(expectedResult, this.shoppingCart.getAvailableSortTypes());
    }

    @Test
    void testIfAddedSortTypeWorks() throws Exception {
        SortProducts sortProductsDescByName = new SortProductsDescByName();
        this.basicListOfProducts = new BasicListOfProducts(sortProductsDescByName);
        this.shoppingCart = new ShoppingCart(this.basicListOfProducts);

        Product product1 = new Product("1", "testA", 12.3);
        Product product2 = new Product("2", "testB", 12.3);

        this.shoppingCart.addProduct(product1);
        this.shoppingCart.addProduct(product2);
        this.shoppingCart.sort("descByName");

        Product[] expectedResult = new Product[5];
        expectedResult[0] = product2;
        expectedResult[1] = product1;

        assertArrayEquals(expectedResult, this.shoppingCart.getListOfProducts());
    }

    @Test
    void testIfSameSortTypeIsAddedTwoTimesSecondOneIsNotAdded() {
        this.basicListOfProducts = new BasicListOfProducts();
        this.shoppingCart = new ShoppingCart(this.basicListOfProducts);

        SortProducts sortProductsAscByPrice = new SortProductsAscByPrice();
        SortProducts sortProductsDescByPrice = new SortProductsDescByPrice();

        this.basicListOfProducts.addSortTypes(sortProductsAscByPrice);

        HashSet<SortProducts> expectedResult = new HashSet<>();
        expectedResult.add(sortProductsAscByPrice);
        expectedResult.add(sortProductsDescByPrice);

        assertEquals(expectedResult, this.shoppingCart.getAvailableSortTypes());
    }

    @Test
    void testIfPassedInConstructorDuplicatedSortTypeIsNotAdded() {
        SortProducts sortProductsAscByPrice = new SortProductsAscByPrice();
        SortProducts sortProductsDescByPrice = new SortProductsDescByPrice();

        this.basicListOfProducts = new BasicListOfProducts(sortProductsAscByPrice);
        this.shoppingCart = new ShoppingCart(this.basicListOfProducts);

        HashSet<SortProducts> expectedResult = new HashSet<>();
        expectedResult.add(sortProductsAscByPrice);
        expectedResult.add(sortProductsDescByPrice);

        assertEquals(expectedResult, this.shoppingCart.getAvailableSortTypes());
    }

    @Test
    void testIfUsedSpecialOfferIsSaved() throws Exception {
        //Client
        //creating shopping cart with two products
        Product testProduct1 = new Product("1", "test", 1000);
        Product testProduct2 = new Product("2", "test", 10);

        //add products to shopping cart
        this.shoppingCart.addProduct(testProduct1);
        this.shoppingCart.addProduct(testProduct2);

        //create special offer order and special offer
        SpecialOffer specialOfferAbove300pln = new SpecialOfferAbove300pln();
        SpecialOfferOrder applySpecialOffer1 = new ApplySpecialOffer(specialOfferAbove300pln);

        //apply special offers orders
        this.shoppingCart.applySpecialOffers(applySpecialOffer1);

        String expectedResult = "specialOfferAbove300pln";

        assertEquals(expectedResult, this.shoppingCart.getCurrentSpecialOffers().get(0));
    }

    @Test
    void testIfTwoSameSpecialOffersCanBeSaved() throws Exception {
        //Client
        //creating shopping cart with two products
        Product testProduct1 = new Product("1", "test", 1000);
        Product testProduct2 = new Product("2", "test", 10);

        //add products to shopping cart
        this.shoppingCart.addProduct(testProduct1);
        this.shoppingCart.addProduct(testProduct2);

        //create special offer order and special offer
        SpecialOffer specialOfferAbove300pln = new SpecialOfferAbove300pln();
        SpecialOfferOrder applySpecialOffer1 = new ApplySpecialOffer(specialOfferAbove300pln);
        SpecialOfferOrder applySpecialOffer2 = new ApplySpecialOffer(specialOfferAbove300pln);

        //apply special offers orders
        this.shoppingCart.applySpecialOffers(applySpecialOffer1, applySpecialOffer2);

        int expectedResult = 1;


        assertEquals(expectedResult, this.shoppingCart.getCurrentSpecialOffers().size());
    }

    //test czy uzycie przykladowej promocji dziala

    //test czy mogą się użyć dwie promocje na raz
}