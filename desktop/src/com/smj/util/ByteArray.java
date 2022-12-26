package com.smj.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ByteArray {
    private byte[] array;
    private int position;
    public final int length;
    public ByteArray(int capacity) {
        this(new byte[capacity]);
    }
    public ByteArray(byte[] array) {
        this.array = array;
        length = array.length;
    }
    public int position() {
        return position;
    }
    public void position(int position) {
        this.position = position;
    }
    public byte[] array() {
        return array;
    }
    public InputStream stream() {
        return new ByteArrayInputStream(array);
    }
    public void write(OutputStream out) throws IOException {
        out.write(array);
    }
    public byte readByte() {
        byte value = array[position];
        position++;
        return value;
    }
    public int readUnsignedByte() {
        return Byte.toUnsignedInt(readByte());
    }
    public short readShort() {
        return (short)((readUnsignedByte() << 8) | readUnsignedByte());
    }
    public int readUnsignedShort() {
        return Short.toUnsignedInt(readShort());
    }
    public int readInt() {
        return (readUnsignedShort() << 16) | readUnsignedShort();
    }
    public long readLong() {
        return ((long)readInt() << 32) | readInt();
    }
    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }
    public boolean readBoolean() {
        return readByte() != 0;
    }
    public char readChar() {
        return (char)readUnsignedByte();
    }
    public String readString(int length) {
        String string = "";
        for (int i = 0; i < length; i++) {
            string += readChar();
        }
        return string;
    }
    public byte[] readArray(int length) {
        byte[] array = new byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = readByte();
        }
        return array;
    }
    public <T extends Enum<T>> T readEnum(Class<T> enumType) {
        return enumType.getEnumConstants()[readUnsignedByte()];
    }
    public ByteArray writeByte(byte value) {
        array[position] = value;
        position++;
        return this;
    }
    public ByteArray writeByte(int value) {
        writeByte((byte)value);
        return this;
    }
    public ByteArray writeShort(short value) {
        writeByte(value >> 8);
        writeByte(value);
        return this;
    }
    public ByteArray writeShort(int value) {
        writeShort((short)value);
        return this;
    }
    public ByteArray writeInt(int value) {
        writeShort(value >> 16);
        writeShort(value);
        return this;
    }
    public ByteArray writeLong(long value) {
        writeInt((int)(value >> 32));
        writeShort((int)value);
        return this;
    }
    public ByteArray writeFloat(float value) {
        writeInt(Float.floatToIntBits(value));
        return this;
    }
    public ByteArray writeDouble(double value) {
        writeLong(Double.doubleToLongBits(value));
        return this;
    }
    public ByteArray writeBoolean(boolean value) {
        writeByte(value ? 1 : 0);
        return this;
    }
    public ByteArray writeChar(char value) {
        writeByte((byte)value);
        return this;
    }
    public ByteArray writeString(String value) {
        for (char character : value.toCharArray()) {
            writeChar(character);
        }
        return this;
    }
    public ByteArray writeArray(byte[] array) {
        for (byte value : array) {
            writeByte(value);
        }
        return this;
    }
    public ByteArray writeEnum(Enum<?> value) {
        writeByte(value.ordinal());
        return this;
    }
}
