package ru.Simonov;

public interface MyMap<T, V> {
    void put(T key, V value);
    V remove(T key);
    V get(T key);
    int getSize();
    boolean isEmpty();
    boolean containsKey(T key);
}
