package com.auberer.compilerdesignlectureproject.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Reader
 */
public class Reader implements IReader {

    private CodeLoc codeLoc;
    private char currentChar;
    private InputStream stream;
    private boolean eof;

    public Reader(String filePath) {
        this.eof = false;
        this.codeLoc = new CodeLoc(1, 0);
        this.currentChar = '0';
        try {
            stream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public char getChar() {
        return currentChar;
    }

    @Override
    public CodeLoc getCodeLoc() {
        return codeLoc;
    }

    @Override
    public void advance() {
        try {
            int ret = stream.read();
            if (ret != -1) {
                this.currentChar = (char) ret;
            } else {
                this.eof = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Fuck");
        }
        if (!isEOF()) {
            codeLoc.column++;
            if (currentChar == '\n') {
                codeLoc.line++;
                codeLoc.column = 1;
            }
        }
    }

    @Override
    public void expect(char expectedChar) {
        if (!isEOF()) {
            if (expectedChar != currentChar) {
                throw new IllegalStateException("Fuck Fuck");
            }
        }
    }

    @Override
    public boolean isEOF() {
        return eof;
    }
}