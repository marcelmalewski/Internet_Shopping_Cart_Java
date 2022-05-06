package com.marcel_malewski.lab3.list_of_products;

import com.marcel_malewski.lab3.Product;

import java.util.Arrays;
import java.util.Comparator;

public class SortProducts {
    private final Product[] listOfProducts;

    public SortProducts(Product[] listOfProducts) {
        this.listOfProducts = listOfProducts;
    }

    public void defaultSort() {
        //desc by price then alphabetical by name
        Comparator<Product> productComparator
                = Comparator.comparing(Product::getPrice)
                .reversed()
                .thenComparing(Product::getName);

        Comparator<Product> productNameComparator_nullLast
                = Comparator.nullsLast(productComparator);

        Arrays.sort(this.listOfProducts, productNameComparator_nullLast);
    }

    public void sortProductsAscByPrice() {
        Comparator<Product> productComparator
                = Comparator.comparing(Product::getPrice);

        Comparator<Product> productNameComparator_nullLast
                = Comparator.nullsLast(productComparator);

        Arrays.sort(this.listOfProducts, productNameComparator_nullLast);
    }

    public void sortProductsDescByPrice() {
        Comparator<Product> productComparator
                = Comparator.comparing(Product::getPrice);

        Comparator<Product> productNameComparator_nullFirst
                = Comparator.nullsFirst(productComparator).reversed();

        Arrays.sort(this.listOfProducts, productNameComparator_nullFirst);
    }

    public void sortProductsAscByName() {
        Comparator<Product> productComparator
                = Comparator.comparing(Product::getName);

        Comparator<Product> productNameComparator_nullLast
                = Comparator.nullsLast(productComparator);

        Arrays.sort(this.listOfProducts, productNameComparator_nullLast);
    }

    public void sortProductsDescByName() {
        Comparator<Product> productComparator
                = Comparator.comparing(Product::getName);

        Comparator<Product> productNameComparator_nullFirst
                = Comparator.nullsFirst(productComparator).reversed();

        Arrays.sort(this.listOfProducts, productNameComparator_nullFirst);
    }
}
