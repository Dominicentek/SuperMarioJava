package com.smj.util.bjson;

import java.util.ArrayList;

public class ListElement {
    private final ArrayList<Object> list = new ArrayList<>();
    public void addObject(ObjectElement value) {
        list.add(value);
    }
    public void addList(ListElement value) {
        list.add(value);
    }
    public void addByte(byte value) {
        list.add(value);
    }
    public void addShort(short value) {
        list.add(value);
    }
    public void addInt(int value) {
        list.add(value);
    }
    public void addLong(long value) {
        list.add(value);
    }
    public void addFloat(float value) {
        list.add(value);
    }
    public void addDouble(double value) {
        list.add(value);
    }
    public void addString(String value) {
        list.add(value);
    }
    public void addBoolean(boolean value) {
        list.add(value);
    }
    public void addNull() {
        list.add(null);
    }
    public void remove(int index) {
        list.remove(index);
    }
    public boolean isObject(int index) {
        return instance(index, ObjectElement.class);
    }
    public boolean isList(int index) {
        return instance(index, ListElement.class);
    }
    public boolean isByte(int index) {
        return instance(index, Byte.class);
    }
    public boolean isShort(int index) {
        return instance(index, Short.class);
    }
    public boolean isInt(int index) {
        return instance(index, Integer.class);
    }
    public boolean isLong(int index) {
        return instance(index, Long.class);
    }
    public boolean isFloat(int index) {
        return instance(index, Float.class);
    }
    public boolean isDouble(int index) {
        return instance(index, Double.class);
    }
    public boolean isString(int index) {
        return instance(index, String.class);
    }
    public boolean isBoolean(int index) {
        return instance(index, Boolean.class);
    }
    public boolean isNull(int index) {
        return list.get(index) == null;
    }
    public ObjectElement getObject(int index) {
        return get(index, ObjectElement.class, "Not an object");
    }
    public ListElement getList(int index) {
        return get(index, ListElement.class, "Not a list");
    }
    public byte getByte(int index) {
        return get(index, Byte.class, "Not a byte");
    }
    public short getShort(int index) {
        return get(index, Short.class, "Not a short");
    }
    public int getInt(int index) {
        return get(index, Integer.class, "Not an int");
    }
    public long getLong(int index) {
        return get(index, Long.class, "Not a long");
    }
    public float getFloat(int index) {
        return get(index, Float.class, "Not a float");
    }
    public double getDouble(int index) {
        return get(index, Double.class, "Not a double");
    }
    public String getString(int index) {
        return get(index, String.class, "Not a string");
    }
    public boolean getBoolean(int index) {
        return get(index, Boolean.class, "Not a boolean");
    }
    public int indexOf(Object element) {
        for (int i = 0; i < size(); i++) {
            if (list.get(i).equals(element)) return i;
        }
        return -1;
    }
    private <T> T get(int index, Class<T> clazz, String errorMsg) {
        Object obj = list.get(index);
        if (clazz.isInstance(index)) throw new BJSONException(errorMsg);
        return clazz.cast(obj);
    }
    public int size() {
        return list.size();
    }
    private boolean instance(int index, Class<?> clazz) {
        Object obj = list.get(index);
        if (obj == null) return false;
        return clazz.isInstance(obj);
    }
    public boolean equals(Object obj) {
        if (obj instanceof ListElement) {
            ListElement element = (ListElement)obj;
            if (element.size() != size()) return false;
            for (int i = 0; i < element.size(); i++) {
                if (!element.list.get(i).equals(list.get(i))) return false;
            }
            return true;
        }
        return false;
    }
}
