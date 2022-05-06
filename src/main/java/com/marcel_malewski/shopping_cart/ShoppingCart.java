package com.marcel_malewski.shopping_cart;

import com.marcel_malewski.shopping_cart.list_of_products.BasicListOfProducts;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import com.marcel_malewski.shopping_cart.special_offer.special_offer_orders.SpecialOfferOrder;
import lombok.Getter;

import java.util.*;

@Getter
public class ShoppingCart {
    private final ListOfProducts basicListOfProducts;
    //remember special offers to faster inform if special offer can be used
    private final ArrayList<String> currentSpecialOffers;
    //products are sorted only when we return them
    private String currentSortType;

    public ShoppingCart(ListOfProducts listOfProducts) {//dodaC przyjmowania listy produktow
        this.basicListOfProducts = listOfProducts;
        this.currentSpecialOffers = new ArrayList<>(){};
        this.currentSortType = "Default";
    }

    //shoppingCart is only invoker
    public void addProduct(Product newProduct) {
        this.basicListOfProducts.addProduct(newProduct);
    }

    //shoppingCart is only invoker
    //return true if product was removed and false if product was not removed
    public boolean removeProduct(Product product) {
        return this.basicListOfProducts.removeProduct(product);
    }

    //shoppingCart is only invoker
    public Optional<Product> getCheapestProduct() {
        return basicListOfProducts.getCheapestProduct();
    }

    //here we combine some methods of basicListOfProducts
    public Product[] getCheapestProducts(int numberOfProducts) {
        ListOfProducts tempListOfProducts = this.basicListOfProducts;
        tempListOfProducts.sortProductsAscByPrice();

        return tempListOfProducts.getProducts(numberOfProducts);
    }

    //shoppingCart is only invoker
    public Optional<Product> getMostExpensiveProduct() {
        return basicListOfProducts.getMostExpensiveProduct();
    }

    //here we combine some methods of basicListOfProducts
    public Product[] getMostExpensiveProducts(int numberOfProducts) {
        ListOfProducts tempBasicListOfProducts = this.basicListOfProducts;
        tempBasicListOfProducts.sortProductsDescByPrice();

        return tempBasicListOfProducts.getProducts(numberOfProducts);
    }

    public void defaultSort() {
        this.currentSortType = "Default";
    }

    public void sortProductsAscByPrice() {
        this.currentSortType = "AscByPrice";
    }

    public void sortProductsDescByPrice() {
        this.currentSortType = "DescByPrice";
    }

    public void sortProductsAscByName() {
        this.currentSortType = "AscByName";
    }

    public void sortProductsDescByName() {
        this.currentSortType = "DescByName";
    }

    //shoppingCart is only invoker
    public double getCostOfShoppingCart() {
        return basicListOfProducts.getSumDiscountPricesOfAllProducts();
    }

    //invoker
    public void applySpecialOffers(SpecialOfferOrder[] specialOfferOrders) throws Exception {
        //array of concreteCommands
        //return special offer that were not used
        for(SpecialOfferOrder specialOfferOrder : specialOfferOrders) {
            if(!this.currentSpecialOffers.contains(specialOfferOrder.getSpecialOffer().getName())){
                if(specialOfferOrder.execute(this.basicListOfProducts)){
                    this.currentSpecialOffers.add(specialOfferOrder.getSpecialOffer().getName());
                }
            }
        }
    }

    //we sort only when we return elements
    public Product[] getBasicListOfProducts() {
        switch (currentSortType) {
            case "Default" -> this.basicListOfProducts.defaultSort();
            case "AscByPrice" -> this.basicListOfProducts.sortProductsAscByPrice();
            case "DescByPrice" -> this.basicListOfProducts.sortProductsDescByPrice();
            case "AscByName" -> this.basicListOfProducts.sortProductsAscByName();
            case "DescByName" -> this.basicListOfProducts.sortProductsDescByName();
        }
        return basicListOfProducts.getListOfProducts();
    }
}
