package com.marcel_malewski.shopping_cart.list_of_products;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.sort.SortProducts;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.stream.IntStream;

@Getter
@ToString
@Setter
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

    private OptionalInt getIndexOfEmptySpace() {
        return IntStream.range(0, this.listOfProducts.length)
                .filter(i -> Objects.isNull(this.listOfProducts[i]))
                .findFirst();
    }

    @Override
    public void addProduct(Product newProduct) {
        OptionalInt optionalIndex = getIndexOfEmptySpace();

        if(optionalIndex.isEmpty()) {
            //expand list of products
            this.listOfProducts = Arrays.copyOf(this.listOfProducts, this.listOfProducts.length + 5);

            optionalIndex = OptionalInt.of(this.listOfProducts.length - 5);
        }

        this.listOfProducts[optionalIndex.getAsInt()] = newProduct;
    }

    private OptionalInt getIndexOfProduct(Product product) {
        return IntStream.range(0, this.listOfProducts.length)
                .filter(i -> Objects.nonNull(this.listOfProducts[i]))
                .filter(i -> this.listOfProducts[i].equals(product))
                .findAny();
    }

    private Product[] getListOfProductsWithoutRedundantNulls() {
        return Arrays.copyOf(this.listOfProducts, this.listOfProducts.length - 5);
    }

    private int currentNumberOfEmptySpaces() {
        return Arrays.stream(this.listOfProducts)
                .filter(Objects::isNull)
                .toArray()
                .length;
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
        OptionalInt optionalIndexOfProduct = getIndexOfProduct(productToDelete);

        if(optionalIndexOfProduct.isEmpty())
            return false;

        this.listOfProducts[optionalIndexOfProduct.getAsInt()] = null;

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
                .findAny();
    }
    @Override
    public void sort(String sortType) throws Exception {
        Optional<SortProducts> optionalSortProducts = tryToGetSortProductsFromAvailableSortTypes(sortType);

        if(optionalSortProducts.isEmpty())
            throw new Exception("BasicListOfProducts cant find expected sortType");

        optionalSortProducts.get().sort(this.listOfProducts);
    }
}
