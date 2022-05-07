package com.marcel_malewski.shopping_cart.list_of_products.sort;

import com.marcel_malewski.shopping_cart.Product;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;

@Getter
public class SortProductsAscByPrice implements SortProducts{
    public String sortType;

    public SortProductsAscByPrice() {
        this.sortType = "ascByPrice";
    }

    @Override
    public void sort(Product[] listOfProducts) {
        Comparator<Product> productComparator
                = Comparator.comparing(Product::getPrice);

        Comparator<Product> productNameComparator_nullLast
                = Comparator.nullsLast(productComparator);

        Arrays.sort(listOfProducts, productNameComparator_nullLast);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.sortType.charAt(0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SortProductsAscByPrice other = (SortProductsAscByPrice) obj;
        return this.sortType.equals(other.getSortType());
    }
}
