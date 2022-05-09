package com.marcel_malewski.shopping_cart;

import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import com.marcel_malewski.shopping_cart.list_of_products.sort.SortProducts;
import com.marcel_malewski.shopping_cart.list_of_products.sort.SortProductsAscByPrice;
import com.marcel_malewski.shopping_cart.list_of_products.sort.SortProductsDescByPrice;
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

    public ShoppingCart(ListOfProducts listOfProducts) {
        this.listOfProducts = listOfProducts;

        SortProducts sortAscByPrice = new SortProductsAscByPrice();
        SortProducts sortDescByPrice = new SortProductsDescByPrice();

        this.listOfProducts.addSortTypes(sortAscByPrice, sortDescByPrice);
        this.currentSpecialOffers = new ArrayList<>(){};
        this.currentSortType = "default";
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
    public Product[] getCheapestProducts(int numberOfProducts) throws Exception {
        ListOfProducts tempListOfProducts = this.listOfProducts;
        tempListOfProducts.sort("ascByPrice");

        return tempListOfProducts.getProducts(numberOfProducts);
    }

    //shoppingCart is only invoker
    public Optional<Product> getMostExpensiveProduct() {
        return listOfProducts.getMostExpensiveProduct();
    }

    //here we combine some methods of listOfProducts
    public Product[] getMostExpensiveProducts(int numberOfProducts) throws Exception {
        ListOfProducts tempListOfProducts = this.listOfProducts;
        tempListOfProducts.sort("descByPrice");

        return tempListOfProducts.getProducts(numberOfProducts);
    }

    public void defaultSort() {
        this.currentSortType = "default";
    }
    public void sort(String newSortType) throws Exception {
        boolean newSortTypeIsAvailable = false;
        //check if new sortType is available
        for(SortProducts sortProducts : this.listOfProducts.getAvailableSortTypes()) {
            if(sortProducts.getSortType().equals(newSortType))
                newSortTypeIsAvailable = true;
        }

        if(newSortTypeIsAvailable) {
            this.currentSortType = newSortType;
        } else {
            throw new Exception("shopping cart cant find expected sortType");
        }
    }

    //shoppingCart is only invoker
    public double getCostOfShoppingCart() {
        return listOfProducts.getSumDiscountPricesOfAllProducts();
    }

    //invoker
    public void applySpecialOffers(SpecialOfferOrder ... specialOfferOrders) throws Exception {
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
    public Product[] getListOfProducts() throws Exception {
        if(currentSortType.equals("default"))
            this.listOfProducts.defaultSort();
        else
            this.listOfProducts.sort(currentSortType);

        return listOfProducts.getListOfProducts();
    }

    public HashSet<SortProducts> getAvailableSortTypes() {
        return this.listOfProducts.getAvailableSortTypes();
    }
}
