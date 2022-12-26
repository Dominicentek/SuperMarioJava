package com.smj.gui.font;

public enum FontFormatting {
    BOLD('b'),
    ITALIC('i'),
    UNDERLINE('u'),
    STRIKETHROUGH('s'),
    OBFUSCATED('o'),
    RAINBOW('h'),
    RESET('r'),
    NO_SHADOW('n'),
    COLOR('c');
    public static final char prefix = '$';
    private char character;
    FontFormatting(char character) {
        this.character = character;
    }
    public char getCharacter() {
        return character;
    }
    public String toString() {
        return prefix + "" + character;
    }
    public static boolean isFormattingChar(char character) {
        for (FontFormatting formatting : values()) {
            if (formatting.character == character) return true;
        }
        return false;
    }
}
