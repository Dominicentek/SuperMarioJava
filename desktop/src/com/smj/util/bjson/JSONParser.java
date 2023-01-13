package com.smj.util.bjson;

import java.util.ArrayList;

public class JSONParser {
    public static ObjectElement parse(String json) {
        ArrayList<Token> tokens = lex(json);
        ArrayList<Token> objectTokens = new ArrayList<>();
        int layer = -1;
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (i == 0 && token.tokenType != TokenType.OBJECT_OPEN) throw new JSONSyntaxException("Object expected", token.lineNumber, token.columnNumber);
            if (token.tokenType == TokenType.OBJECT_OPEN) layer++;
            if (token.tokenType == TokenType.OBJECT_CLOSE) layer--;
            objectTokens.add(token);
            if (layer == -1) break;
        }
        if (layer >= 0) throw new JSONSyntaxException("Unexpected EOF");
        objectTokens.remove(0);
        return parseObject(objectTokens);
    }
    private static ObjectElement parseObject(ArrayList<Token> tokens) {
        ObjectElement element = new ObjectElement();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.tokenType != TokenType.STRING) throw new JSONSyntaxException("String expected", token.lineNumber, token.columnNumber);
            String key = (String)token.tokenValue;
            i++;
            token = tokens.get(i);
            if (token.tokenType != TokenType.COLON) throw new JSONSyntaxException("Colon expected", token.lineNumber, token.columnNumber);
            i++;
            token = tokens.get(i);
            if (token.tokenType == TokenType.STRING) element.setString(key, (String)token.tokenValue);
            else if (token.tokenType == TokenType.NUMBER) element.setDouble(key, (Double)token.tokenValue);
            else if (token.tokenType == TokenType.TRUE) element.setBoolean(key, true);
            else if (token.tokenType == TokenType.FALSE) element.setBoolean(key, false);
            else if (token.tokenType == TokenType.NULL) element.setNull(key);
            else if (token.tokenType == TokenType.OBJECT_OPEN) {
                int layer = 0;
                ArrayList<Token> objectTokens = new ArrayList<>();
                i++;
                int j;
                for (j = i; j < tokens.size(); j++) {
                    Token t = tokens.get(j);
                    if (t.tokenType == TokenType.OBJECT_OPEN) layer++;
                    if (t.tokenType == TokenType.OBJECT_CLOSE) layer--;
                    objectTokens.add(t);
                    if (layer == -1) break;
                }
                i = j;
                element.setObject(key, parseObject(objectTokens));
            }
            else if (token.tokenType == TokenType.LIST_OPEN) {
                int layer = 0;
                ArrayList<Token> listTokens = new ArrayList<>();
                i++;
                int j;
                for (j = i; j < tokens.size(); j++) {
                    Token t = tokens.get(j);
                    if (t.tokenType == TokenType.LIST_OPEN) layer++;
                    if (t.tokenType == TokenType.LIST_CLOSE) layer--;
                    listTokens.add(t);
                    if (layer == -1) break;
                }
                i = j;
                element.setList(key, parseList(listTokens));
            }
            else throw new JSONSyntaxException("Unexpected token", token.lineNumber, token.columnNumber);
            i++;
            token = tokens.get(i);
            boolean comma = token.tokenType == TokenType.COMMA;
            if (token.tokenType == TokenType.OBJECT_CLOSE) return element;
            else if (!comma) throw new JSONSyntaxException("Comma expected", token.lineNumber, token.columnNumber);
        }
        throw new JSONSyntaxException("this shoudlnt happen wtf");
    }
    private static ListElement parseList(ArrayList<Token> tokens) {
        ListElement element = new ListElement();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.tokenType == TokenType.STRING) element.addString((String)token.tokenValue);
            else if (token.tokenType == TokenType.NUMBER) element.addDouble((Double)token.tokenValue);
            else if (token.tokenType == TokenType.TRUE) element.addBoolean(true);
            else if (token.tokenType == TokenType.FALSE) element.addBoolean(false);
            else if (token.tokenType == TokenType.NULL) element.addNull();
            else if (token.tokenType == TokenType.OBJECT_OPEN) {
                int layer = 0;
                ArrayList<Token> objectTokens = new ArrayList<>();
                i++;
                int j;
                for (j = i; j < tokens.size(); j++) {
                    Token t = tokens.get(j);
                    if (t.tokenType == TokenType.OBJECT_OPEN) layer++;
                    if (t.tokenType == TokenType.OBJECT_CLOSE) layer--;
                    objectTokens.add(t);
                    if (layer == -1) break;
                }
                i = j;
                element.addObject(parseObject(objectTokens));
            }
            else if (token.tokenType == TokenType.LIST_OPEN) {
                int layer = 0;
                ArrayList<Token> listTokens = new ArrayList<>();
                i++;
                int j;
                for (j = i; j < tokens.size(); j++) {
                    Token t = tokens.get(j);
                    if (t.tokenType == TokenType.LIST_OPEN) layer++;
                    if (t.tokenType == TokenType.LIST_CLOSE) layer--;
                    listTokens.add(t);
                    if (layer == -1) break;
                }
                i = j;
                element.addList(parseList(listTokens));
            }
            else throw new JSONSyntaxException("Unexpected token", token.lineNumber, token.columnNumber);
            i++;
            token = tokens.get(i);
            boolean comma = token.tokenType == TokenType.COMMA;
            if (token.tokenType == TokenType.LIST_CLOSE) return element;
            else if (!comma) throw new JSONSyntaxException("Comma expected", token.lineNumber, token.columnNumber);
        }
        throw new JSONSyntaxException("this shoudlnt happen wtf");
    }
    private static ArrayList<Token> lex(String text) {
        ArrayList<Token> tokens = new ArrayList<>();
        int lineNumber = 1;
        int columnNumber = 1;
        String word = null;
        String string = null;
        int stringLineNumber = 0;
        int stringColumnNumber = 0;
        boolean backslash = false;
        String unicode = null;
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if (unicode == null) {
                if (backslash) {
                    backslash = false;
                    if (character == 'n') string += "\n";
                    else if (character == 'b') string += "\b";
                    else if (character == 'f') string += "\f";
                    else if (character == 'r') string += "\r";
                    else if (character == 't') string += "\t";
                    else if (character == '\"') string += "\"";
                    else if (character == '\\') string += "\\";
                    else if (character == '/') string += "/";
                    else if (character == 'u') unicode = "";
                    else throw new JSONSyntaxException("Invalid escape character: '" + character + "'", lineNumber, columnNumber - 1);
                }
                else {
                    if (character == '"') {
                        if (word != null) {
                            Token token = new Token();
                            token.lineNumber = stringLineNumber;
                            token.columnNumber = stringColumnNumber;
                            if (word.equals("null")) token.tokenType = TokenType.NULL;
                            else if (word.equals("true")) token.tokenType = TokenType.TRUE;
                            else if (word.equals("false")) token.tokenType = TokenType.FALSE;
                            else {
                                token.tokenType = TokenType.NUMBER;
                                token.tokenValue = Double.parseDouble(word);
                            }
                            tokens.add(token);
                            word = null;
                        }
                        if (string == null) string = "";
                        else {
                            Token token = new Token();
                            token.lineNumber = stringLineNumber;
                            token.columnNumber = stringColumnNumber;
                            token.tokenType = TokenType.STRING;
                            token.tokenValue = string;
                            tokens.add(token);
                            string = null;
                        }
                    }
                    else if (character == '\\' && string != null) {
                        backslash = true;
                    }
                    else if (string != null) string += character;
                    else if (character == '{') {
                        Token token = new Token();
                        token.lineNumber = lineNumber;
                        token.columnNumber = columnNumber;
                        token.tokenType = TokenType.OBJECT_OPEN;
                        tokens.add(token);
                    }
                    else if (character == '}') {
                        Token token = new Token();
                        token.lineNumber = lineNumber;
                        token.columnNumber = columnNumber;
                        token.tokenType = TokenType.OBJECT_CLOSE;
                        tokens.add(token);
                    }
                    else if (character == '[') {
                        Token token = new Token();
                        token.lineNumber = lineNumber;
                        token.columnNumber = columnNumber;
                        token.tokenType = TokenType.LIST_OPEN;
                        tokens.add(token);
                    }
                    else if (character == ']') {
                        Token token = new Token();
                        token.lineNumber = lineNumber;
                        token.columnNumber = columnNumber;
                        token.tokenType = TokenType.LIST_CLOSE;
                        tokens.add(token);
                    }
                    else if (character == ':') {
                        Token token = new Token();
                        token.lineNumber = lineNumber;
                        token.columnNumber = columnNumber;
                        token.tokenType = TokenType.COLON;
                        tokens.add(token);
                    }
                    else if (character == ',') {
                        Token token = new Token();
                        token.lineNumber = lineNumber;
                        token.columnNumber = columnNumber;
                        token.tokenType = TokenType.COMMA;
                        tokens.add(token);
                    }
                    else if (isAlphanumeric(character) || character == '.' || character == '+' || character == '-') {
                        if (word == null) {
                            word = "";
                            stringLineNumber = lineNumber;
                            stringColumnNumber = columnNumber;
                        }
                        word += character;
                    }
                    else if (isSymbol(character)) {
                        throw new JSONSyntaxException("Invalid symbol: '" + character + "'", lineNumber, columnNumber);
                    }
                    else {
                        if (word != null) {
                            Token token = new Token();
                            token.lineNumber = stringLineNumber;
                            token.columnNumber = stringColumnNumber;
                            if (word.equals("null")) token.tokenType = TokenType.NULL;
                            else if (word.equals("true")) token.tokenType = TokenType.TRUE;
                            else if (word.equals("false")) token.tokenType = TokenType.FALSE;
                            else {
                                token.tokenType = TokenType.NUMBER;
                                token.tokenValue = Double.parseDouble(word);
                            }
                            tokens.add(token);
                            word = null;
                        }
                    }
                }
            }
            else {
                if (isHexadecimal(character)) unicode += character;
                else throw new JSONSyntaxException("Not a hexadecimal digit: '" + character + "'", lineNumber, columnNumber);
                if (unicode.length() == 4) {
                    int codepoint = Integer.parseInt(unicode, 16);
                    string += new String(Character.toChars(codepoint));
                    unicode = null;
                }
            }
            columnNumber++;
            if (character == '\n') {
                if (string != null) throw new JSONSyntaxException("Unterminated string", stringLineNumber, stringColumnNumber);
                columnNumber = 1;
                lineNumber++;
            }
        }
        return tokens;
    }
    private static boolean isSymbol(char character) {
        return character >= 33 && character <= 126 && !isLetter(character) && !isNumber(character);
    }
    private static boolean isLetter(char character) {
        return (character >= 'A' && character <= 'Z') || (character >= 'a' && character <= 'z');
    }
    private static boolean isNumber(char character) {
        return character >= '0' && character <= '9';
    }
    private static boolean isAlphanumeric(char character) {
        return isLetter(character) || isNumber(character);
    }
    private static boolean isHexadecimal(char character) {
        return isNumber(character) || ((character >= 'A' && character <= 'F') || (character >= 'a' && character <= 'f'));
    }
    private static class Token {
        private Object tokenValue;
        private TokenType tokenType;
        private int lineNumber;
        private int columnNumber;
        public String toString() {
            return "JSONTOKEN v=" + tokenValue + ";t=" + tokenType + ";l=" + lineNumber + ";c=" + columnNumber;
        }
    }
    private enum TokenType {
        OBJECT_OPEN,
        OBJECT_CLOSE,
        LIST_OPEN,
        LIST_CLOSE,
        COLON,
        COMMA,
        STRING,
        NUMBER,
        NULL,
        TRUE,
        FALSE
    }
    private static class JSONSyntaxException extends RuntimeException {
        private JSONSyntaxException(String message) {
            super(message);
        }
        private JSONSyntaxException(String message, int lineNumber, int columnNumber) {
            super(message + " (" + lineNumber + ";" + columnNumber + ")");
        }
    }
}
