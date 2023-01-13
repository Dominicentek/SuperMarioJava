package com.smj.util;

import com.smj.game.cutscene.Actor;

import java.lang.reflect.Field;

public class FieldInstance {
    public Object instance = null;
    public Field field;
    public FieldInstance(Object instance, String fieldName) {
        this.instance = instance;
        try {
            field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public FieldInstance(Class<?> type, String fieldName) {
        try {
            field = type.getDeclaredField(fieldName);
            field.setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Object get() {
        try {
            return field.get(instance);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void set(Object value) {
        try {
            field.set(instance, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean equals(Object obj) {
        if (obj instanceof FieldInstance) {
            return field.equals(((FieldInstance)obj).field) && (instance == null || instance.equals(((FieldInstance)obj).instance));
        }
        return false;
    }
}
