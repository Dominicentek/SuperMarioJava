package com.smj.jmario.tile;

public class BlankTileModificationException extends RuntimeException {
    public BlankTileModificationException() {
        super("Cannot modify a blank tile");
    }
}
