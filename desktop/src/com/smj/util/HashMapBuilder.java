package com.smj.util;

import java.util.HashMap;

public class HashMapBuilder<K, V> {
    private HashMap<K, V> map = new HashMap<>();
    public HashMapBuilder<K, V> add(K key, V value) {
        map.put(key, value);
        return this;
    }
    public HashMap<K, V> get() {
        return map;
    }
}
