package com.auberer.compilerdesignlectureproject.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader implements IReader {

    private BufferedReader bufferedReader;
    private int currentChar;
    private int lineNumber;
    private int columnNumber;

    public Reader(String filePath) {
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            currentChar = bufferedReader.read();
            lineNumber = 1;
            columnNumber = 1;
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            System.err.println("Error message: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            System.err.println("Error message: " + e.getMessage());
        }
    }

    @Override
    public char getChar() {
        return (char) currentChar;
    }

    @Override
    public CodeLoc getCodeLoc() {
        return new CodeLoc(lineNumber, columnNumber);
    }

    @Override
    public void advance() {
        try {
            currentChar = bufferedReader.read();
            if (currentChar == '\n') {
                lineNumber++;
                columnNumber = 0;
            } else {
                columnNumber++;
            }
        } catch (IOException e) {
            System.err.println("Error advancing in file.");
            System.err.println("Error message: " + e.getMessage());
        }
    }

    @Override
    public void expect(char c) throws Exception {
        if (currentChar != c) {
            throw new Exception("Expected character " + c + " at " + getCodeLoc() + " but got " + getChar());
        }
    }

    @Override
    public boolean isEOF() {
        return currentChar == -1;
    }
}