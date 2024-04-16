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
        try {
            this.stream = new FileInputStream(filePath);
            // get first char of file
            advance();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // This one is for testing
    public Reader(InputStream stream) {
        this.eof = false;
        this.codeLoc = new CodeLoc(1, 0);
        this.stream = stream;
        // get first char of file
        advance();
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
