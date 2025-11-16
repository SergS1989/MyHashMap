package ru.Simonov;

import java.util.Objects;

public class MyHashMap<T, V> implements MyMap<T, V> {
    private static final int CAPACITY = 16;
    private int size = 0;
    private Node<T, V>[] table;

    public MyHashMap() {
        table = new Node[CAPACITY];
    }

    public MyHashMap(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Объем должен быть больше нуля");
        this.table = new Node[capacity];
    }

    private static class Node<T, V> {
        private int hash;
        T key;
        V value;
        Node<T, V> next;

        public Node(int hash, T key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    @Override
    public void put(T key, V value) {
        int hash = Objects.hashCode(key);
        int index = Math.abs(hash) % table.length;

        Node<T, V> curr = table[index];
        if (curr == null) {
            table[index] = new Node<>(hash, key, value);
            size++;
            return;
        }

        Node<T, V> prev = null;
        while (curr != null) {
            if (curr.hash == hash && Objects.equals(curr.key, key)) {
                curr.value = value;
                return;
            }
            prev = curr;
            curr = curr.next;
        }
        prev.next = new Node<>(hash, key, value);
        size++;
    }

    @Override
    public V remove(T key) {
        int hash = Objects.hashCode(key);
        int index = Math.abs(hash) % table.length;
        Node<T, V> curr = table[index];
        if (curr == null) {
            return null;
        }

        Node<T, V> prev = null;
        V val;
        while (curr != null) {
            if (curr.hash == hash && Objects.equals(curr.key, key)) {
                val = curr.value;
                if (prev == null) {
                    table[index] = curr.next;
                } else {
                    prev.next = curr.next;
                    curr.next = null;
                }
                size--;
                return val;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    @Override
    public V get(T key) {
        int hash = Objects.hashCode(key);
        int index = Math.abs(hash) % table.length;
        Node<T, V> curr = table[index];
        if (curr == null) {
            return null;
        }

        while (curr != null) {
            if (curr.hash == hash && Objects.equals(curr.key, key)) {
                return curr.value;
            }
            curr = curr.next;
        }

        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(T key) {
        return get(key) != null;
    }
}
