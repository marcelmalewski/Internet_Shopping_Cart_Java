package com.marcel_malewski.shopping_cart;

import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import com.marcel_malewski.shopping_cart.special_offer.special_offer_orders.SpecialOfferOrder;
import lombok.Getter;

import java.util.*;

@Getter
public class ShoppingCart {
    private final ListOfProducts listOfProducts;
    //remember special offers to faster inform if special offer can be used
    private final ArrayList<String> currentSpecialOffers;
    //products are sorted only when we return them
    private String currentSortType;

    public ShoppingCart(ListOfProducts listOfProducts) {//dodaC przyjmowania listy produktow
        this.listOfProducts = listOfProducts;
        this.currentSpecialOffers = new ArrayList<>(){};
        this.currentSortType = "Default";
    }

    //shoppingCart is only invoker
    public void addProduct(Product newProduct) {
        this.listOfProducts.addProduct(newProduct);
    }

    //shoppingCart is only invoker
    //return true if product was removed and false if product was not removed
    public boolean removeProduct(Product product) {
        return this.listOfProducts.removeProduct(product);
    }

    //shoppingCart is only invoker
    public Optional<Product> getCheapestProduct() {
        return listOfProducts.getCheapestProduct();
    }

    //here we combine some methods of listOfProducts
    public Product[] getCheapestProducts(int numberOfProducts) {
        ListOfProducts tempListOfProducts = this.listOfProducts;
        tempListOfProducts.sortProductsAscByPrice();

        return tempListOfProducts.getProducts(numberOfProducts);
    }

    //shoppingCart is only invoker
    public Optional<Product> getMostExpensiveProduct() {
        return listOfProducts.getMostExpensiveProduct();
    }

    //here we combine some methods of listOfProducts
    public Product[] getMostExpensiveProducts(int numberOfProducts) {
        ListOfProducts tempListOfProducts = this.listOfProducts;
        tempListOfProducts.sortProductsDescByPrice();

        return tempListOfProducts.getProducts(numberOfProducts);
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
        return listOfProducts.getSumDiscountPricesOfAllProducts();
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
    public Product[] getlistOfProducts() {
        switch (currentSortType) {
            case "Default" -> this.listOfProducts.defaultSort();
            case "AscByPrice" -> this.listOfProducts.sortProductsAscByPrice();
            case "DescByPrice" -> this.listOfProducts.sortProductsDescByPrice();
            case "AscByName" -> this.listOfProducts.sortProductsAscByName();
            case "DescByName" -> this.listOfProducts.sortProductsDescByName();
        }
        return listOfProducts.getListOfProducts();
    }
}
