package com.marcel_malewski.shopping_cart.list_of_products;

import com.marcel_malewski.shopping_cart.Product;

import java.util.Optional;

public interface ListOfProducts {
    void addProduct(Product product);
    boolean removeProduct(Product product);

    Product[] getListOfProducts();
    Product[] getProducts(int numberOfProducts);
    Optional<Product> getCheapestProduct();
    Optional<Product> getMostExpensiveProduct();
    double getSumDiscountPricesOfAllProducts();
    double getSumPricesOfAllProducts();
    
    void defaultSort();
    void sortProductsAscByPrice();
    void sortProductsDescByPrice();
    void sortProductsAscByName();
    void sortProductsDescByName();
}
