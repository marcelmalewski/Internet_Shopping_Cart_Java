package com.marcel_malewski.shopping_cart.list_of_products;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.sort.SortProducts;

import java.util.HashSet;
import java.util.Optional;

public interface ListOfProducts {
    void addProduct(Product product);
    boolean removeProduct(Product product);

    Product[] getListOfProducts();
    void setListOfProducts(Product[] listOfProducts);
    HashSet<SortProducts> getAvailableSortTypes();
    Product[] getProducts(int numberOfProducts);
    Optional<Product> getCheapestProduct();
    Optional<Product> getMostExpensiveProduct();
    double getSumDiscountPricesOfAllProducts();
    double getSumPricesOfAllProducts();

    void defaultSort();
    void sort(String sortType) throws Exception;
    void addSortTypes(SortProducts ... sortProducts);
}
