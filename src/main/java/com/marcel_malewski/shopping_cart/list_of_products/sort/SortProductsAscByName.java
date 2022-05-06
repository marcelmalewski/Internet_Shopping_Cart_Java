package com.marcel_malewski.shopping_cart.list_of_products.sort;

import com.marcel_malewski.shopping_cart.Product;

import java.util.Arrays;
import java.util.Comparator;

public class SortProductsAscByName implements SortProducts{
    @Override
    public void sort(Product[] listOfProducts) {
        Comparator<Product> productComparator
                = Comparator.comparing(Product::getName);

        Comparator<Product> productNameComparator_nullLast
                = Comparator.nullsLast(productComparator);

        Arrays.sort(listOfProducts, productNameComparator_nullLast);
    }
}
