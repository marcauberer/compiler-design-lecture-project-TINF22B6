package com.auberer.compilerdesignlectureproject.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Reader implements IReader {

    private int line;
    private int columnNumber;
    private String content;
    private int charIndex;

    public Reader(String filePath) {
        try {
            Path path = Paths.get(filePath);
            content = Files.readString(path);
            charIndex = 0;
            line = 1;
            columnNumber = 1;
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            System.err.println("Error message: " + e.getMessage());
        }
    }

    @Override
    public char getChar() {
        if (charIndex < content.length()) {
            return content.charAt(charIndex);
        }
        return (char) -1; // EOF
    }

    @Override
    public CodeLoc getCodeLoc() {
        return new CodeLoc(line, columnNumber);
    }

    @Override
    public void advance() {
        if (charIndex < content.length()) {
            charIndex++;
            if (content.charAt(charIndex - 1) == '\n') {
                line++;
                columnNumber = 1;
            } else {
                columnNumber++;
            }
        }
    }

    @Override
    public void expect(char c) throws Exception {
        char currentChar = getChar();
        if (currentChar != c) {
            throw new Exception("Expected character " + c + " at " + getCodeLoc() + " but got " + currentChar);
        }
        advance();
    }

    @Override
    public boolean isEOF() {
        return charIndex >= content.length();
    }
}



