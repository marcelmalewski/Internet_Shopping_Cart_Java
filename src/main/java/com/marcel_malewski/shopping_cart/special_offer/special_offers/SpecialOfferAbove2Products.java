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

    private Product[] getListOfProductsWithoutNullsAndFreeProducts(Product[] listOfProducts) {
        return Arrays.stream(listOfProducts)
                .filter(Objects::nonNull)
                .filter(product -> product.getDiscountPrice() > 0)
                .toArray(Product[]::new);
    }

    private void setProductFreeInOriginalArray(Product[] filteredListOfProducts, Product[] listOfProducts, int finalIndexOfProductToSetFree) throws Exception {
        //one more time check if indexOfProductsToSetFree is correct
        OptionalInt indexOfProductInOriginalList = IntStream.range(0, listOfProducts.length)
                .filter(a -> filteredListOfProducts[finalIndexOfProductToSetFree].equals(listOfProducts[a]))
                .findFirst();

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

            //we setDiscountPrice to zero for current cheapest product
            final int finalIndexOfProductToSetFree = indexOfProductToSetFree;

            filteredListOfProducts[finalIndexOfProductToSetFree].setDiscountPrice(0);

            setProductFreeInOriginalArray(filteredListOfProducts, listOfProducts, finalIndexOfProductToSetFree);

            //setting products free is from end of list to beginning of the list
            indexOfProductToSetFree--;
        }
    }

    @Override
    public void apply(ListOfProducts listOfProducts) throws Exception {
        Product[] filteredListOfProducts = getListOfProductsWithoutNullsAndFreeProducts(listOfProducts.getListOfProducts());

        Comparator<Product> productComparator
                = Comparator.comparing(Product::getPrice).reversed();

        Arrays.sort(filteredListOfProducts, productComparator);

        //products are sorted desc by price, so we just take two products from begging of list
        //and set free one product from the end of list
        setProductsFree(filteredListOfProducts, listOfProducts.getListOfProducts());
    }

    private int countNumberOfNotFreeProducts(Product[] listOfProducts) {
        return Arrays.stream(listOfProducts)
                .filter(Objects::nonNull)
                .filter(product -> product.getDiscountPrice() > 0)
                .toArray()
                .length;
    }

    @Override
    public boolean canApply(ListOfProducts listOfProducts) {
        return countNumberOfNotFreeProducts(listOfProducts.getListOfProducts()) > 2;
    }
}
