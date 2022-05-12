package com.marcel_malewski.shopping_cart.special_offer.special_offers;

import com.marcel_malewski.shopping_cart.Product;
import com.marcel_malewski.shopping_cart.list_of_products.ListOfProducts;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.IntStream;

@ToString
@Getter
public class SpecialOfferAbove2Products implements SpecialOffer {
    private final String name;

    public SpecialOfferAbove2Products() {
        this.name = "specialOfferAbove2Products";
    }

    private Product[] getFilteredListOfProducts(int numberOfNotFreeProducts, Product[] listOfProducts) {
        Product[] filteredListOfProducts = new Product[numberOfNotFreeProducts];

        int index = 0;
        for(Product product : listOfProducts) {
            if(Objects.isNull(product)) break;

            filteredListOfProducts[index] = product;
            index++;
        }

        return filteredListOfProducts;
    }

    private void setProductFreeInOriginalArray(Product[] filteredListOfProducts, Product[] listOfProducts, int finalIndexOfProductToSetFree) throws Exception {
        OptionalInt indexOfProductInOriginalList = IntStream.range(0, listOfProducts.length)
                .filter(a -> filteredListOfProducts[finalIndexOfProductToSetFree].equals(listOfProducts[a]))
                .findFirst();

        //komentarz
        if(indexOfProductInOriginalList.isEmpty())
            throw new Exception("cant get index of product");

        listOfProducts[indexOfProductInOriginalList.getAsInt()].setDiscountPrice(0);
    }

    private void setProductsFree(Product[] filteredListOfProducts, Product[] listOfProducts) throws Exception {
        int indexOfProductToSetFree = filteredListOfProducts.length - 1;

        for(int i=0; i<filteredListOfProducts.length; i += 2) {
            //if we do not have two not free products we can break loop
            if(filteredListOfProducts[i].getDiscountPrice() == 0 || filteredListOfProducts[i+1].getDiscountPrice() == 0 )
                break;

            //komentarz
            if(indexOfProductToSetFree <= i + 1)
                break;

            //we setDiscountPrice to zero for cheapest product
            final int finalIndexOfProductToSetFree = indexOfProductToSetFree;

            filteredListOfProducts[finalIndexOfProductToSetFree].setDiscountPrice(0);

            setProductFreeInOriginalArray(filteredListOfProducts, listOfProducts, finalIndexOfProductToSetFree);

            indexOfProductToSetFree--;
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

            if(product.getDiscountPrice() == 0)
                continue;

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
