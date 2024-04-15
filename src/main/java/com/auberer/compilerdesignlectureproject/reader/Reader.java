package com.auberer.compilerdesignlectureproject.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Reader implements IReader {
    private final String fileContents;

    private final CodeLoc codeLoc = new CodeLoc(1, 0);
    private char currChar = '\0';
    private int index = 0;

    public Reader(String filename) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            throw new RuntimeException("Source file cannot be opened: " + filename, e);
        }

        this.fileContents = String.join("", lines);

        this.advance();
    }

    @Override
    public char getChar() {
        return this.currChar;
    }

    @Override
    public CodeLoc getCodeLoc() {
        return this.codeLoc;
    }

    @Override
    public void advance() {
        if (this.isEOF()) {
            return;
        }

        this.index++;
        this.currChar = fileContents.charAt(index);

        if (currChar == '\n') {
            this.codeLoc.line++;
            this.codeLoc.column = 0;
        } else {
            this.codeLoc.column++;
        }
    }

    @Override
    public void expect(char c) {
        if (this.currChar != c) {
            throw new RuntimeException("Expected " + c + " but found " + this.currChar);
        }

        this.advance();
    }

    @Override
    public boolean isEOF() {
        return currChar == '\0';
    }
}