package com.marcel_malewski.shopping_cart.list_of_products.sort;

import com.marcel_malewski.shopping_cart.Product;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;

@Getter
public class SortProductsDescByPrice implements SortProducts{
    public String sortType;

    public SortProductsDescByPrice() {
        this.sortType = "descByPrice";
    }

    @Override
    public void sort(Product[] listOfProducts) {
        Comparator<Product> productComparator
                = Comparator.comparing(Product::getPrice);

        Comparator<Product> productNameComparator_nullFirst
                = Comparator.nullsFirst(productComparator).reversed();

        Arrays.sort(listOfProducts, productNameComparator_nullFirst);
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
        SortProductsDescByPrice other = (SortProductsDescByPrice) obj;
        return this.sortType.equals(other.getSortType());
    }
}
