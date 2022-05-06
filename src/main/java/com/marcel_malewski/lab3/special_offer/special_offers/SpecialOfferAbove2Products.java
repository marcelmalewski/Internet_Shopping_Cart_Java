package com.marcel_malewski.lab3.special_offer.special_offers;

import com.marcel_malewski.lab3.list_of_products.ListOfProducts;
import com.marcel_malewski.lab3.Product;

import java.util.*;
import java.util.stream.IntStream;

public class SpecialOfferAbove2Products extends SpecialOffer {
    public SpecialOfferAbove2Products(String name) {
        this.name = name;
    }

    private Product[] getFilteredListOfProducts(int numberOfNotFreeProducts, Product[] listOfProducts) {
        Product[] filteredListOfProducts = new Product[numberOfNotFreeProducts];

        int index = 0;
        for(Product product : listOfProducts) {
            if(Objects.nonNull(product)) {
                filteredListOfProducts[index] = product;
                index++;
            }
        }

        return filteredListOfProducts;
    }

    private void setProductsFree(Product[] filteredListOfProducts, Product[] listOfProducts) throws Exception {
        int indexOfProductToSetFree = filteredListOfProducts.length - 1;

        for(int i=0; i<filteredListOfProducts.length; i += 2) {
            //if we have two not free products
            if(filteredListOfProducts[i].getDiscountPrice() > 0 && filteredListOfProducts[i+1].getDiscountPrice() > 0 ){
                //we setDiscountPrice to zero for cheapest product
                if(indexOfProductToSetFree > i + 1) {
                    final int finalIndexOfProductToSetFree = indexOfProductToSetFree;

                    filteredListOfProducts[finalIndexOfProductToSetFree].setDiscountPrice(0);

                    OptionalInt indexOfProductInOriginalList = IntStream.range(0, listOfProducts.length)
                            .filter(a -> filteredListOfProducts[finalIndexOfProductToSetFree].equals(listOfProducts[a]))
                            .findFirst();

                    if(indexOfProductInOriginalList.isPresent()) {
                        listOfProducts[indexOfProductInOriginalList.getAsInt()].setDiscountPrice(0);
                    } else {
                        throw new Exception("cant get index of product");
                    }

                    indexOfProductToSetFree--;
                } else break;
            } else
                //if we do not have two not free products we can break loop
                break;
        }
    }

    @Override
    public void apply(ListOfProducts listOfProducts) throws Exception {
        int numberOfNotFreeProducts = countNumberOfNotFreeProducts(listOfProducts.getListOfProducts());

        //remove nulls and free products from list
        Product[] filteredListOfProducts = getFilteredListOfProducts(numberOfNotFreeProducts, listOfProducts.getListOfProducts());

        //sort list descending by price
        Comparator<Product> productComparator
                = Comparator.comparing(Product::getPrice).reversed();

        Arrays.sort(filteredListOfProducts, productComparator);

        //products are sorted desc by price, so we just take two products from begging of list
        //and set free one product from the end of list
        setProductsFree(filteredListOfProducts, listOfProducts.getListOfProducts());
    }

    private int countNumberOfNotFreeProducts(Product[] listOfProducts) {
        int numberOfProducts = 0;

        for(Product product : listOfProducts) {
            if(Objects.isNull(product))
                break;
            else if(product.getDiscountPrice() > 0)
                numberOfProducts++;
        }

        return numberOfProducts;
    }

    @Override
    public boolean canApply(ListOfProducts listOfProducts) {
        int numberOfNotFreeProducts = countNumberOfNotFreeProducts(listOfProducts.getListOfProducts());

        //we need at least 3 not free products
        return numberOfNotFreeProducts > 2;
    }
}
