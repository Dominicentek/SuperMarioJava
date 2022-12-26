package com.smj.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RNG {
    private static final Random random = new Random();
    public static int range(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    public static int bound(int bound) {
        return random.nextInt(bound);
    }
    public static boolean chance(float ratio) {
        return random.nextFloat() <= ratio;
    }
    public static float range(float min, float max) {
        return random.nextFloat() * (max - min + 1) + min;
    }
    public static float bound(float bound) {
        return random.nextFloat() * bound;
    }
    public static double range(double min, double max) {
        return random.nextDouble() * (max - min + 1) + min;
    }
    public static double bound(double bound) {
        return random.nextDouble() * bound;
    }
    public static void seed(long seed) {
        random.setSeed(seed);
    }
    public static void randomSeed() {
        seed(System.currentTimeMillis());
        seed(random.nextLong());
    }
    public static <T> T choose(T... elements) {
        return elements[bound(elements.length)];
    }
    public static <T> T choose(List<T> elements) {
        return elements.get(bound(elements.size()));
    }
}
