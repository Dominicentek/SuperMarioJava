package com.smj.util;

import java.awt.Rectangle;
import java.util.ArrayList;

public class AnimatedRectangle extends Rectangle {
    private static final ArrayList<AnimatedRectangle> instances = new ArrayList<>();
    public Rectangle[] rectangles;
    public int time;
    public int timeout;
    public int currentIndex = 0;
    public AnimatedRectangle(int time, Rectangle... rectangles) {
        this.rectangles = rectangles;
        this.time = time;
        timeout = time;
    }
    public static void updateAll() {
        for (AnimatedRectangle rectangle : instances) {
            rectangle.update();
        }
    }
    public void update() {
        timeout--;
        if (timeout == 0) {
            timeout = time;
            currentIndex++;
            if (currentIndex == rectangles.length) currentIndex = 0;
        }
    }
    public Rectangle getCurrentRectangle() {
        return rectangles[currentIndex];
    }
}
