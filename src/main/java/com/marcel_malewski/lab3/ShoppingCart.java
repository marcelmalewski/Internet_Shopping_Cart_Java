package com.marcel_malewski.lab3;

import com.marcel_malewski.lab3.list_of_products.ListOfProducts;
import com.marcel_malewski.lab3.special_offer.special_offer_orders.SpecialOfferOrder;
import lombok.Getter;

import java.util.*;

@Getter
public class ShoppingCart {
    private final ListOfProducts listOfProducts;
    //remember special offers to faster inform if special offer can be used
    private final ArrayList<String> currentSpecialOffers;
    //we need products to be sorted only when we return them
    private String currentSortType;

    public ShoppingCart() {
        this.listOfProducts = new ListOfProducts();
        this.currentSpecialOffers = new ArrayList<>(){};
        this.currentSortType = "Default";
    }

    //shoppingCart is only invoker
    public void addProduct(Product newProduct) {
        this.listOfProducts.addElement(newProduct);
    }

    //shoppingCart is only invoker
    //return true if product was removed and false if product was not removed
    public boolean removeProduct(Product product) {
        return this.listOfProducts.removeElement(product);
    }

    //shoppingCart is only invoker
    public Optional<Product> getCheapestProduct() {
        return listOfProducts.getCheapestProduct();
    }

    //here we combine some methods of listOfProducts
    public Product[] getCheapestProducts(int numberOfProducts) {
        ListOfProducts tempListOfProducts = this.listOfProducts;
        tempListOfProducts.sortElementsAscByPrice();

        return tempListOfProducts.peekElements(numberOfProducts);
    }

    //shoppingCart is only invoker
    public Optional<Product> getMostExpensiveProduct() {
        return listOfProducts.getMostExpensiveProduct();
    }

    //here we combine some methods of listOfProducts
    public Product[] getMostExpensiveProducts(int numberOfProducts) {
        ListOfProducts tempListOfProducts = this.listOfProducts;
        tempListOfProducts.sortElementsDescByPrice();

        return tempListOfProducts.peekElements(numberOfProducts);
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
        return listOfProducts.sumDiscountPricesOfAllProducts();
    }

    //invoker
    public void applySpecialOffers(SpecialOfferOrder[] specialOfferOrders) throws Exception {
        //array of concreteCommands
        //return special offer that were not used
        for(SpecialOfferOrder specialOfferOrder : specialOfferOrders) {
            if(!this.currentSpecialOffers.contains(specialOfferOrder.getSpecialOffer().getName())){
                if(specialOfferOrder.execute(this.listOfProducts)){
                    this.currentSpecialOffers.add(specialOfferOrder.getSpecialOffer().getName());
                }
            }
        }
    }

    //we sort only when we return elements
    public Product[] getListOfProducts() {
        switch (currentSortType) {
            case "Default" -> this.listOfProducts.defaultSort();
            case "AscByPrice" -> this.listOfProducts.sortElementsAscByPrice();
            case "DescByPrice" -> this.listOfProducts.sortElementsDescByPrice();
            case "AscByName" -> this.listOfProducts.sortElementsAscByName();
            case "DescByName" -> this.listOfProducts.sortElementsDescByName();
        }
        return listOfProducts.getListOfProducts();
    }
}
