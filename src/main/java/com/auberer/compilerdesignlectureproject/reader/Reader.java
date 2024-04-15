package com.auberer.compilerdesignlectureproject.reader;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

/**
 * Reader
 */
public class Reader implements IReader {

    private CodeLoc codeLoc;
    private Scanner sc;
    private char c;

    public Reader(File file) throws FileNotFoundException {
        this.codeLoc = new CodeLoc(0, 0);
        this.c = '0';
        this.sc = new Scanner(file);
    }

    @Override
    public char getChar() {
        c = (char) sc.nextByte();
        advance();
        return c;
    }

    @Override
    public CodeLoc getCodeLoc() {
        return codeLoc;
    }

    @Override
    public void advance() {
        codeLoc.column++;
        if (c == '\n') {
            codeLoc.line++;
            codeLoc.column = 0;
        }
    }

    @Override
    public void expect(char expectedChar) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'expect'");
    }

    @Override
    public boolean isEOF() {
        return this.sc.hasNext();
    }

}
