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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.code.charAt(0);
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

        Product product = (Product) obj;
        return this.code.equals(product.getCode()) &&
                this.name.equals(product.getName()) &&
                this.price == product.getPrice() &&
                this.discountPrice == product.getDiscountPrice();
    }
}
