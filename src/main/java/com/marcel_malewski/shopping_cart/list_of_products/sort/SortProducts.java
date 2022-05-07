package com.marcel_malewski.shopping_cart.list_of_products.sort;

import com.marcel_malewski.shopping_cart.Product;

public interface SortProducts {
    void sort(Product[] listOfProducts);
    String getSortType();
    int hashCode();
    boolean equals(Object obj);
}
