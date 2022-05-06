package com.marcel_malewski.lab3.list_of_products;

public interface List<T extends Comparable<T> > {
    void addElement(T element);
    boolean removeElement(T element);
    T[] peekElements(int numberOfElements);
    void defaultSort();
}
