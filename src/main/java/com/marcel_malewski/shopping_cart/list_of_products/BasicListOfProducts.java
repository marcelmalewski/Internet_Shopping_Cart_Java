package com.marcel_malewski.shopping_cart.list_of_products;

import com.marcel_malewski.shopping_cart.Product;
import lombok.Getter;

import java.util.*;

@Getter
public class BasicListOfProducts implements ListOfProducts {
    private Product[] listOfProducts;
    private final SortProducts sortProducts;

    public BasicListOfProducts() {
        //we start with 5 free spaces
        this.listOfProducts = new Product[5];
        this.sortProducts = new SortProducts(this.listOfProducts);
    }

    private OptionalInt getIndexOfEmptySpace(Product[] listOfProducts) {
        OptionalInt optionalIndex = OptionalInt.empty();

        for(int i=0; i<listOfProducts.length; i++) {
            if(Objects.isNull(listOfProducts[i])) {
                optionalIndex = OptionalInt.of(i);
                break;
            }
        }

        return optionalIndex;
    }

    @Override
    public void addProduct(Product newProduct) {
        //try to get index of empty space
        OptionalInt optionalIndex = getIndexOfEmptySpace(this.listOfProducts);

        if(optionalIndex.isEmpty()) {
            Product[] biggerListOfProducts =
                    Arrays.copyOf(this.listOfProducts, this.listOfProducts.length + 5);

            optionalIndex = OptionalInt.of(this.listOfProducts.length);

            this.listOfProducts = biggerListOfProducts;
        }

        //add element
        this.listOfProducts[optionalIndex.getAsInt()] = newProduct;
    }

    private Product[] getListOfProductsWithoutNulls() {
        //5 free spaces are always added or removed
        Product[] filteredListOfProducts = new Product[this.listOfProducts.length - 5];

        int index = 0;
        for(Product product : this.listOfProducts) {
            if(Objects.nonNull(product)) {
                filteredListOfProducts[index] = product;
                index++;
            }
        }

        return filteredListOfProducts;
    }

    @Override
    public boolean removeProduct(Product productToDelete){
        //one element will be deleted, so we start with one emptySpace
        int numberOfEmptySpaces = 1;
        boolean elementWasRemoved = false;

        //we try to remove element and count number of empty spaces in same loop
        for(int i=0; i<this.listOfProducts.length; i++) {
            if(Objects.isNull(this.listOfProducts[i]))
                numberOfEmptySpaces++;

            else if(this.listOfProducts[i].getCode().equals(productToDelete.getCode())) {
                this.listOfProducts[i] = null;
                elementWasRemoved = true;
            }
        }

        if(elementWasRemoved && numberOfEmptySpaces == 5 && this.listOfProducts.length > 5) {
            //we remove excess of free space, because in this list of products we have max 4 empty spaces
            this.listOfProducts = getListOfProductsWithoutNulls();
        }

        //we inform if element was removed
        return elementWasRemoved;
    }

    //return n number of elements but not remove them from list
    public Product[] getProducts(int numberOfElements) {
        return Arrays.copyOf(this.listOfProducts, numberOfElements);
    }

    public Optional<Product> getCheapestProduct() {
        //cheapest element is optional because list of products can be empty
        Optional<Product> optionalCheapestProduct = Optional.empty();

        if(Objects.nonNull(this.listOfProducts[0])){
            optionalCheapestProduct = Optional.of(this.listOfProducts[0]);

            for(Product product: this.listOfProducts) {
                //after one null there are only nulls
                if(Objects.isNull(product))
                    return optionalCheapestProduct;

                else if(product.getPrice() < optionalCheapestProduct.get().getPrice())
                    optionalCheapestProduct = Optional.of(product);

            }
        }

        return optionalCheapestProduct;
    }

    public Optional<Product> getMostExpensiveProduct() {
        //most expensive element is optional because list of products can be empty
        Optional<Product> optionalExpensiveProduct = Optional.empty();

        if(Objects.nonNull(this.listOfProducts[0])){
            optionalExpensiveProduct = Optional.of(this.listOfProducts[0]);

            for(Product product: this.listOfProducts) {
                //after one null there are only nulls
                if(Objects.isNull(product))
                    return optionalExpensiveProduct;

                else if(product.getPrice() > optionalExpensiveProduct.get().getPrice())
                    optionalExpensiveProduct = Optional.of(product);

            }
        }

        return optionalExpensiveProduct;
    }

    @Override
    public double getSumDiscountPricesOfAllProducts() {
        double sum = 0;

        for (Product product : this.listOfProducts) {
            //ignore nulls
            if (!Objects.isNull(product))
                sum += product.getDiscountPrice();
        }

        return Math.round(sum * Math.pow(10, 2))
                / Math.pow(10, 2);
    }

    @Override
    public double getSumPricesOfAllProducts() {
        double sum = 0;

        for (Product product : this.listOfProducts) {
            //ignore nulls
            if (!Objects.isNull(product))
                sum += product.getPrice();
        }

        return Math.round(sum * Math.pow(10, 2))
                / Math.pow(10, 2);
    }

    @Override
    public void defaultSort() {
        //desc by price then alphabetical by name
        this.sortProducts.defaultSort();
    }

    @Override
    public void sortProductsAscByPrice() {
        this.sortProducts.sortProductsAscByPrice();
    }
    @Override
    public void sortProductsDescByPrice() {
        this.sortProducts.sortProductsDescByPrice();
    }
    @Override
    public void sortProductsAscByName() {
        this.sortProducts.sortProductsAscByName();
    }
    @Override
    public void sortProductsDescByName() {
        this.sortProducts.sortProductsDescByName();
    }
}
