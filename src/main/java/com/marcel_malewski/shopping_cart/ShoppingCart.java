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

    public void addProduct(Product newProduct) {
        this.listOfProducts.addProduct(newProduct);
    }

    public boolean removeProduct(Product product) {
        return this.listOfProducts.removeProduct(product);
    }

    public Optional<Product> getCheapestProduct() {
        return listOfProducts.getCheapestProduct();
    }

    public Product[] getCheapestProducts(int numberOfProducts) throws Exception {
        ListOfProducts tempListOfProducts = this.listOfProducts;
        tempListOfProducts.sort("ascByPrice");

        return tempListOfProducts.getProducts(numberOfProducts);
    }

    public Optional<Product> getMostExpensiveProduct() {
        return listOfProducts.getMostExpensiveProduct();
    }

    public Product[] getMostExpensiveProducts(int numberOfProducts) throws Exception {
        ListOfProducts tempListOfProducts = this.listOfProducts;
        tempListOfProducts.sort("descByPrice");

        return tempListOfProducts.getProducts(numberOfProducts);
    }

    public void defaultSort() {
        this.currentSortType = "default";
    }

    private boolean sortTypeIsAvailable(String sortType) {
        return this.listOfProducts.getAvailableSortTypes().stream()
                .anyMatch(availableSortProducts -> availableSortProducts.getSortType().equals(sortType));
    }

    public void sort(String newSortType) throws Exception {
        if(!sortTypeIsAvailable(newSortType))
            throw new Exception("shopping cart cant find expected sortType");

        this.currentSortType = newSortType;
    }

    public double getCurrentCostOfShoppingCart() {
        return listOfProducts.getSumDiscountPricesOfAllProducts();
    }

    public void applySpecialOffers(SpecialOfferOrder ... specialOfferOrders) {
        Arrays.stream(specialOfferOrders)
                .filter(specialOfferOrder -> !this.currentSpecialOffers.contains(specialOfferOrder.specialOffer().getName()))
                .forEach(specialOfferOrder -> {
                    try {
                        if(!specialOfferOrder.execute(this.listOfProducts))
                            return;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    this.currentSpecialOffers.add(specialOfferOrder.specialOffer().getName());
                });
    }

    public Product[] getListOfProducts() throws Exception {
        if(currentSortType.equals("default"))
            this.listOfProducts.defaultSort();
        else
            this.listOfProducts.sort(currentSortType);

        return listOfProducts.getListOfProducts();
    }

    public void addNewSortType(SortProducts... sortProducts) {
        this.listOfProducts.addSortTypes(sortProducts);
    }

    public HashSet<SortProducts> getAvailableSortTypes() {
        return this.listOfProducts.getAvailableSortTypes();
    }
}
