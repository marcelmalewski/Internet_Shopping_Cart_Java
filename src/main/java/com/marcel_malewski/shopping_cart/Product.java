package com.marcel_malewski.shopping_cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {
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
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Product product)) {
            return false;
        }

        return this.code.equals(product.getCode()) &&
                this.name.equals(product.getName()) &&
                this.price == product.getPrice() &&
                this.discountPrice == product.getDiscountPrice();
    }
}
