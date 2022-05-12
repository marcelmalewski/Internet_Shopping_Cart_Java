package com.marcel_malewski.shopping_cart.list_of_products.sort;

import com.marcel_malewski.shopping_cart.Product;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Comparator;

@ToString
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

        //in list of products can be null
        Comparator<Product> productNameComparator_nullFirst
                = Comparator.nullsFirst(productComparator).reversed();

        Arrays.sort(listOfProducts, productNameComparator_nullFirst);
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int result = 1;
        result = prime * result + this.sortType.charAt(0) + this.sortType.length();

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if (obj == this)
            return true;
        if (getClass() != obj.getClass())
            return false;

        SortProductsDescByPrice s = (SortProductsDescByPrice) obj;
        return this.sortType.equals(s.getSortType());
    }
}
