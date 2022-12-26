package com.smj.util;

import java.util.ArrayList;

public class Queue<E> {
    private ArrayList<E> list = new ArrayList<>();
    public void push(E element) {
        list.add(element);
    }
    public E pull() {
        return list.remove(0);
    }
    public E peek() {
        return list.get(0);
    }
    public int size() {
        return list.size();
    }
    public boolean empty() {
        return list.isEmpty();
    }
}
