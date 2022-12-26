package com.smj.util;

public class InstanceBuilder<T> {
    private final Class<T> type;
    private final Object[] args;
    public InstanceBuilder(Class<T> type, Object... args) {
        this.type = type;
        this.args = args;
    }
    public T build() {
        try {
            Class<?>[] constructorTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                constructorTypes[i] = args[i].getClass();
            }
            return type.getConstructor(constructorTypes).newInstance(args);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
