package com.marcel_malewski.shopping_cart.list_of_products;

public interface List<T extends Comparable<T> > {
    void addElement(T element);
    boolean removeElement(T element);
    void defaultSort();
}
