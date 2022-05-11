package com.marcel_malewski.shopping_cart.list_of_products;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.sort.SortProducts;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
public class BasicListOfProducts implements ListOfProducts {
    private Product[] listOfProducts;
    private final HashSet<SortProducts> availableSortTypes;

    public BasicListOfProducts() {
        //we start with 5 free spaces
        this.listOfProducts = new Product[5];
        this.availableSortTypes = new HashSet<>();
    }

    public BasicListOfProducts(SortProducts ... sortProducts) {
        //we start with 5 free spaces
        this.listOfProducts = new Product[5];
        this.availableSortTypes = new HashSet<>(List.of(sortProducts));
    }

    private OptionalInt getIndexOfEmptySpace(Product[] listOfProducts) {
        OptionalInt optionalIndex = OptionalInt.empty();

        for(int i=0; i<listOfProducts.length; i++) {
            if(Objects.nonNull(listOfProducts[i]))
                continue;

            optionalIndex = OptionalInt.of(i);
            break;
        }

        return optionalIndex;
    }

    @Override
    public void addProduct(Product newProduct) {
        //try to get index of empty space
        OptionalInt optionalIndex = getIndexOfEmptySpace(this.listOfProducts);

        if(optionalIndex.isEmpty()) {
            this.listOfProducts = Arrays.copyOf(this.listOfProducts, this.listOfProducts.length + 5);

            optionalIndex = OptionalInt.of(this.listOfProducts.length - 5);
        }

        //add element
        this.listOfProducts[optionalIndex.getAsInt()] = newProduct;
    }

    private Product[] getListOfProductsWithoutRedundantNulls() {
        //5 free spaces are always added or removed
        Product[] filteredListOfProducts = new Product[this.listOfProducts.length - 5];

        int index = 0;
        for(Product product : this.listOfProducts) {
            if(Objects.isNull(product))
                break;

            filteredListOfProducts[index] = product;
            index++;
        }

        return filteredListOfProducts;
    }

    private int currentNumberOfEmptySpaces() {
        int numberOfEmptySpaces = 0;

        for (Product listOfProduct : this.listOfProducts) {
            if(Objects.nonNull(listOfProduct))
                continue;

            numberOfEmptySpaces++;
        }

        return numberOfEmptySpaces;
    }

    private Optional<Integer> getIndexOfProduct(Product product) {
        Optional<Integer> optionalIndexOfProduct = Optional.empty();

        for(int i=0; i<this.listOfProducts.length; i++) {
            if(Objects.isNull(this.listOfProducts[i]))
                break;

            if (!this.listOfProducts[i].equals(product))
                continue;

            optionalIndexOfProduct = Optional.of(i);
        }

        return optionalIndexOfProduct;
    }

    private void cleanListOfProductsFromRedundantFreeSpaces() {
        int numberOfEmptySpaces = currentNumberOfEmptySpaces();

        if(numberOfEmptySpaces < 5 || this.listOfProducts.length <= 5)
            return;

        //we remove excess of free space, because in this list of products we have max 4 empty spaces
        this.listOfProducts = getListOfProductsWithoutRedundantNulls();
    }

    @Override
    public boolean removeProduct(Product productToDelete){
        Optional<Integer> optionalIndexOfProduct = getIndexOfProduct(productToDelete);

        if(optionalIndexOfProduct.isEmpty())
            return false;

        this.listOfProducts[optionalIndexOfProduct.get()] = null;

        cleanListOfProductsFromRedundantFreeSpaces();

        return true;
    }

    public Product[] getProducts(int numberOfElements) {
        return Arrays.copyOf(this.listOfProducts, numberOfElements);
    }

    @Override
    public double getSumDiscountPricesOfAllProducts() {
        double sum = 0;

        for (Product product : this.listOfProducts) {
            //ignore nulls
            if (Objects.isNull(product))
                break;

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
            if (Objects.isNull(product))
                break;

            sum += product.getPrice();
        }

        return Math.round(sum * Math.pow(10, 2))
                / Math.pow(10, 2);
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

                if(product.getPrice() < optionalCheapestProduct.get().getPrice())
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

                if(product.getPrice() > optionalExpensiveProduct.get().getPrice())
                    optionalExpensiveProduct = Optional.of(product);

            }
        }

        return optionalExpensiveProduct;
    }

    @Override
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

    @Override
    public void addSortTypes(SortProducts... sortProducts) {
        this.availableSortTypes.addAll(List.of(sortProducts));
    }

    @Override
    public void sort(String sortType) throws Exception {
        boolean sortTypeIsAvailable = false;

        for(SortProducts sortProducts : this.availableSortTypes) {
            if(sortProducts.getSortType().equals(sortType)) {
                sortTypeIsAvailable = true;
                sortProducts.sort(this.listOfProducts);
                break;
            }
        }

        if(!sortTypeIsAvailable)
            throw new Exception("BasicListOfProducts cant find expected sortType");
    }
}
