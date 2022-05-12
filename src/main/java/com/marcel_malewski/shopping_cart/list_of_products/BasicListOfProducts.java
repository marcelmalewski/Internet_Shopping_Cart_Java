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
        //start with 5 free spaces
        this.listOfProducts = new Product[5];
        this.availableSortTypes = new HashSet<>();
    }

    public BasicListOfProducts(SortProducts ... sortProducts) {
        //start with 5 free spaces
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
            //expand list of products
            this.listOfProducts = Arrays.copyOf(this.listOfProducts, this.listOfProducts.length + 5);

            optionalIndex = OptionalInt.of(this.listOfProducts.length - 5);
        }

        this.listOfProducts[optionalIndex.getAsInt()] = newProduct;
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

    private Product[] getListOfProductsWithoutRedundantNulls() {
        return Arrays.copyOf(this.listOfProducts, this.listOfProducts.length - 5);
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
        double sum = Arrays.stream(this.listOfProducts)
                .filter(Objects::nonNull)
                .mapToDouble(Product::getDiscountPrice)
                .sum();

        return Math.round(sum * Math.pow(10, 2))
                / Math.pow(10, 2);
    }

    @Override
    public double getSumPricesOfAllProducts() {
        double sum = Arrays.stream(this.listOfProducts)
                .filter(Objects::nonNull)
                .mapToDouble(Product::getPrice)
                .sum();

        return Math.round(sum * Math.pow(10, 2))
                / Math.pow(10, 2);
    }

    public Optional<Product> getCheapestProduct() {
        return Arrays.stream(this.listOfProducts)
                .filter(Objects::nonNull)
                .min(Comparator.comparing(Product::getPrice));
    }

    public Optional<Product> getMostExpensiveProduct() {
        return Arrays.stream(this.listOfProducts)
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Product::getPrice));
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

    private Optional<SortProducts> tryToGetSortProductsFromAvailableSortTypes(String sortType) {
        return this.availableSortTypes.stream()
                .filter(Objects::nonNull)
                .filter(sortProducts -> sortProducts.getSortType().equals(sortType))
                .findFirst();
    }
    @Override
    public void sort(String sortType) throws Exception {
        Optional<SortProducts> optionalSortProducts = tryToGetSortProductsFromAvailableSortTypes(sortType);

        if(optionalSortProducts.isEmpty())
            throw new Exception("BasicListOfProducts cant find expected sortType");

        optionalSortProducts.get().sort(this.listOfProducts);
    }
}
