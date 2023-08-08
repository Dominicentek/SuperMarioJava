package com.smj.game.challenge;

public class Medals {
    public static final Comparator LESS_THAN = (value1, value2) -> value1 <= value2;
    public static final Comparator GREATER_THAN = (value1, value2) -> value1 >= value2;
    public static final Comparator ALWAYS = (value1, value2) -> true;
    public int gold;
    public int silver;
    public int bronze;
    public Comparator comparator = ALWAYS;
    public Medals(int gold, int silver, int bronze) {
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }
    public Medals comparator(Comparator comparator) {
        this.comparator = comparator;
        return this;
    }
    public int medal(int value) {
        if (comparator.compare(value, gold)) return 3;
        if (comparator.compare(value, silver)) return 2;
        if (comparator.compare(value, bronze)) return 1;
        return 0;
    }
    public interface Comparator {
        boolean compare(int value1, int value2);
    }
}
