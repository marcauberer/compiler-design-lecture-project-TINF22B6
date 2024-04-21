package com.auberer.compilerdesignlectureproject.reader;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Reader implements IReader {
    @Getter
    private final String fileContents;

    private final CodeLoc codeLoc = new CodeLoc(0, -1);
    private char currChar = '\\';
    private int index = -1;

    public Reader(String filename) {
        this.fileContents = this.readFile(filename);
        this.advance();
    }

    private String readFile(String filename) {
        try {
            return Files.readString(Paths.get(filename));
        } catch (IOException e) {
            throw new RuntimeException("Source file cannot be opened: " + filename, e);
        }
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

        this.currChar = this.fileContents.charAt(this.index);

        if (this.currChar == '\n') {
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
        return this.index == this.fileContents.length() - 1;
    }
}
