package com.marcel_malewski.shopping_cart.list_of_products.sort;

import com.marcel_malewski.shopping_cart.Product;

public interface SortProducts {
    String getSortType();

    void sort(Product[] listOfProducts);
}
