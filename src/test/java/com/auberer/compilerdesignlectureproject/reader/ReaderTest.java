package com.auberer.compilerdesignlectureproject.reader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {
    private Reader reader;

    @Test
    void readFile() {
        reader = new Reader("testReader.txt");

        reader.expect('H');
        assertTrue(reader.getCodeLoc().getColumn() == 1 && reader.getCodeLoc().getLine() == 0);

        reader.expect('e');
        assertTrue(reader.getCodeLoc().getColumn() == 2 && reader.getCodeLoc().getLine() == 0);

        reader.expect('l');
        assertTrue(reader.getCodeLoc().getColumn() == 3 && reader.getCodeLoc().getLine() == 0);

        reader.expect('l');
        assertTrue(reader.getCodeLoc().getColumn() == 4 && reader.getCodeLoc().getLine() == 0);

        reader.expect('o');
        assertTrue(reader.getCodeLoc().getColumn() == 5 && reader.getCodeLoc().getLine() == 0);

        reader.expect('\r');
        assertTrue(reader.getCodeLoc().getColumn() == 0 && reader.getCodeLoc().getLine() == 1);

        reader.expect('\n');
        assertTrue(reader.getCodeLoc().getColumn() == 1 && reader.getCodeLoc().getLine() == 1);

        reader.expect('!');
        assertTrue(reader.getCodeLoc().getColumn() == 1 && reader.getCodeLoc().getLine() == 1);

        assertTrue(reader.isEOF());
    }

    @Test
    void readFileError() {
        reader = new Reader("testReader.txt");

        reader.expect('H');
        assertTrue(reader.getCodeLoc().getColumn() == 1 && reader.getCodeLoc().getLine() == 0);

        reader.expect('e');
        assertTrue(reader.getCodeLoc().getColumn() == 2 && reader.getCodeLoc().getLine() == 0);

        reader.expect('l');
        assertTrue(reader.getCodeLoc().getColumn() == 3 && reader.getCodeLoc().getLine() == 0);

        reader.expect('l');
        assertTrue(reader.getCodeLoc().getColumn() == 4 && reader.getCodeLoc().getLine() == 0);

        Throwable e = assertThrows(RuntimeException.class, () -> reader.expect('!'));
        assertEquals("Expected ! but found o", e.getMessage());
    }
}
