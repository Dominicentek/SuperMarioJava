package com.smj.util.mask;

public class Circle {
    public int x;
    public int y;
    public int r;
    public int s;
    public float a;
    public Circle(int r, int s) {
        this.r = r;
        this.s = s;
        this.a = s / 255f;
    }
    public Circle copy() {
        return new Circle(r, s);
    }
}
