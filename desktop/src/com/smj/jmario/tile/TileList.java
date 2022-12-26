package com.smj.jmario.tile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class TileList<T> implements Iterable<T> {
    private final ArrayList<T> list = new ArrayList<>();
    private final T blankTile;
    private Class<T> type;
    public TileList() {
        blankTile = createBlankTile();
        list.add(blankTile);
    }
    public abstract T createBlankTile();
    public final T get(int index) {
        if (index == 0) return blankTile;
        return list.get(index);
    }
    public final TileList set(int index, T tile) {
        if (index == 0) throw new IllegalArgumentException("Cannot change blank tile");
        list.set(index, tile);
        return this;
    }
    public final int amount() {
        return list.size();
    }
    public final TileList clear() {
        while (list.size() > 1) {
            list.remove(1);
        }
        return this;
    }
    public final TileList add(T tile) {
        list.add(tile);
        return this;
    }
    public final TileList remove(int index) {
        if (index == 0) throw new IllegalArgumentException("Cannot remove blank tile");
        list.remove(index);
        return this;
    }
    public final T[] array() {
        T[] array = (T[])Array.newInstance(type, list.size() - 1);
        for (int i = 1; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
    public final Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 1;
            public boolean hasNext() {
                return index < amount() - 1;
            }
            public T next() {
                T value = list.get(index);
                index++;
                return value;
            }
        };
    }
}
