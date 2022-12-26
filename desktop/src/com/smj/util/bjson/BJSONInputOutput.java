package com.smj.util.bjson;

import com.smj.util.ByteArray;

public class BJSONInputOutput {
    public static ObjectElement readObject(ByteArray data) throws Exception {
        ObjectElement element = new ObjectElement();
        int elementType;
        while ((elementType = data.readUnsignedByte()) != 0) {
            int nameLength = data.readUnsignedShort();
            String name = data.readString(nameLength);
            if (elementType == 1) element.setObject(name, readObject(data));
            if (elementType == 2) element.setList(name, readList(data));
            if (elementType == 3) element.setByte(name, data.readByte());
            if (elementType == 4) element.setShort(name, data.readShort());
            if (elementType == 5) element.setInt(name, data.readInt());
            if (elementType == 6) element.setLong(name, data.readLong());
            if (elementType == 7) element.setFloat(name, data.readFloat());
            if (elementType == 8) element.setDouble(name, data.readDouble());
            if (elementType == 9) element.setString(name, data.readString(data.readUnsignedShort()));
            if (elementType == 10) element.setBoolean(name, data.readBoolean());
            if (elementType == 11) element.setNull(name);
        }
        return element;
    }
    public static ListElement readList(ByteArray data) throws Exception {
        ListElement element = new ListElement();
        int size = data.readInt();
        for (int i = 0; i < size; i++) {
            int elementType = data.readByte();
            if (elementType == 1) element.addObject(readObject(data));
            if (elementType == 2) element.addList(readList(data));
            if (elementType == 3) element.addByte(data.readByte());
            if (elementType == 4) element.addShort(data.readShort());
            if (elementType == 5) element.addInt(data.readInt());
            if (elementType == 6) element.addLong(data.readLong());
            if (elementType == 7) element.addFloat(data.readFloat());
            if (elementType == 8) element.addDouble(data.readDouble());
            if (elementType == 9) element.addString(data.readString(data.readUnsignedShort()));
            if (elementType == 10) element.addBoolean(data.readBoolean());
            if (elementType == 11) element.addNull();
        }
        return element;
    }
    public static byte[] write(ObjectElement element) {
        ByteArray array = new ByteArray(calculateSize(element));
        for (String name : element.keys()) {
            if (element.isObject(name)) array.writeByte(1);
            if (element.isList(name)) array.writeByte(2);
            if (element.isByte(name)) array.writeByte(3);
            if (element.isShort(name)) array.writeByte(4);
            if (element.isInt(name)) array.writeByte(5);
            if (element.isLong(name)) array.writeByte(6);
            if (element.isFloat(name)) array.writeByte(7);
            if (element.isDouble(name)) array.writeByte(8);
            if (element.isString(name)) array.writeByte(9);
            if (element.isBoolean(name)) array.writeByte(10);
            if (element.isNull(name)) array.writeByte(11);
            array.writeShort(name.length());
            array.writeString(name);
            if (element.isObject(name)) array.writeArray(write(element.getObject(name)));
            if (element.isList(name)) array.writeArray(write(element.getList(name)));
            if (element.isByte(name)) array.writeByte(element.getByte(name));
            if (element.isShort(name)) array.writeShort(element.getShort(name));
            if (element.isInt(name)) array.writeInt(element.getInt(name));
            if (element.isLong(name)) array.writeLong(element.getLong(name));
            if (element.isFloat(name)) array.writeFloat(element.getFloat(name));
            if (element.isDouble(name)) array.writeDouble(element.getDouble(name));
            if (element.isString(name)) array.writeShort(element.getString(name).length()).writeString(element.getString(name));
            if (element.isBoolean(name)) array.writeBoolean(element.getBoolean(name));
        }
        array.writeByte(0);
        return array.array();
    }
    public static byte[] write(ListElement element) {
        ByteArray array = new ByteArray(calculateSize(element));
        array.writeInt(element.size());
        for (int i = 0; i < element.size(); i++) {
            if (element.isObject(i)) array.writeByte(1);
            if (element.isList(i)) array.writeByte(2);
            if (element.isByte(i)) array.writeByte(3);
            if (element.isShort(i)) array.writeByte(4);
            if (element.isInt(i)) array.writeByte(5);
            if (element.isLong(i)) array.writeByte(6);
            if (element.isFloat(i)) array.writeByte(7);
            if (element.isDouble(i)) array.writeByte(8);
            if (element.isString(i)) array.writeByte(9);
            if (element.isBoolean(i)) array.writeByte(10);
            if (element.isNull(i)) array.writeByte(11);
            if (element.isObject(i)) array.writeArray(write(element.getObject(i)));
            if (element.isList(i)) array.writeArray(write(element.getList(i)));
            if (element.isByte(i)) array.writeByte(element.getByte(i));
            if (element.isShort(i)) array.writeShort(element.getShort(i));
            if (element.isInt(i)) array.writeInt(element.getInt(i));
            if (element.isLong(i)) array.writeLong(element.getLong(i));
            if (element.isFloat(i)) array.writeFloat(element.getFloat(i));
            if (element.isDouble(i)) array.writeDouble(element.getDouble(i));
            if (element.isString(i)) array.writeShort(element.getString(i).length()).writeString(element.getString(i));
            if (element.isBoolean(i)) array.writeBoolean(element.getBoolean(i));
        }
        return array.array();
    }
    public static int calculateSize(ObjectElement element) {
        int size = 0;
        for (String name : element.keys()) {
            size += 3 + name.length();
            if (element.isObject(name)) size += calculateSize(element.getObject(name));
            if (element.isList(name)) size += calculateSize(element.getList(name));
            if (element.isByte(name)) size += 1;
            if (element.isShort(name)) size += 2;
            if (element.isInt(name)) size += 4;
            if (element.isLong(name)) size += 8;
            if (element.isFloat(name)) size += 4;
            if (element.isDouble(name)) size += 8;
            if (element.isString(name)) size += 2 + element.getString(name).length();
            if (element.isBoolean(name)) size += 1;
        }
        size++;
        return size;
    }
    public static int calculateSize(ListElement element) {
        int size = 4;
        for (int i = 0; i < element.size(); i++) {
            size++;
            if (element.isObject(i)) size += calculateSize(element.getObject(i));
            if (element.isList(i)) size += calculateSize(element.getList(i));
            if (element.isByte(i)) size += 1;
            if (element.isShort(i)) size += 2;
            if (element.isInt(i)) size += 4;
            if (element.isLong(i)) size += 8;
            if (element.isFloat(i)) size += 4;
            if (element.isDouble(i)) size += 8;
            if (element.isString(i)) size += 2 + element.getString(i).length();
            if (element.isBoolean(i)) size += 1;
        }
        return size;
    }
}
