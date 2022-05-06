package com.marcel_malewski.lab3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product implements Comparable<Product> {
    //code is unique for every product
    private final String code;
    private final String name;
    private final double price;
    private double discountPrice;

    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = price;
    }

    @Override
    public int compareTo(Product product) {
        return this.name.compareTo(product.getName());
    }

    public boolean equalProducts(Product product) {
        return this.code.equals(product.getCode()) &&
                this.name.equals(product.getName()) &&
                this.price == product.getPrice() &&
                this.discountPrice == product.getDiscountPrice();
    }
}
