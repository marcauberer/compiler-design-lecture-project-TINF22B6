package com.auberer.compilerdesignlectureproject.reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReaderTest {

    @Test
    void reader() {
        String validFilePath = "src/test/java/com/auberer/compilerdesignlectureproject/reader/test.txt";
        Reader reader = new Reader(validFilePath);

        assertEquals('T', reader.getChar());
        CodeLoc expectedFirstCharLoc = new CodeLoc(1, 1);
        assertEquals(expectedFirstCharLoc, reader.getCodeLoc());
    }

    @Test
    void getCodeLoc() {
        String filePath = "src/test/java/com/auberer/compilerdesignlectureproject/reader/test.txt";
        Reader reader = new Reader(filePath);

        CodeLoc expectedFirstCharLoc = new CodeLoc(1, 1);
        assertEquals(expectedFirstCharLoc, reader.getCodeLoc());

        reader.advance();
        CodeLoc expectedSecondCharLoc = new CodeLoc(1, 2);
        assertEquals(expectedSecondCharLoc, reader.getCodeLoc());
    }

    @Test
    void advance() {
        String filePath = "src/test/java/com/auberer/compilerdesignlectureproject/reader/test.txt";
        Reader reader = new Reader(filePath);

        CodeLoc expectedFirstCharLoc = new CodeLoc(1, 1);
        assertEquals(expectedFirstCharLoc, reader.getCodeLoc());

        reader.advance();

        CodeLoc expectedSecondCharLoc = new CodeLoc(1, 2);
        assertEquals(expectedSecondCharLoc, reader.getCodeLoc());

        for (int i = 0; i < 20; i++) {
            reader.advance();
        }

        CodeLoc expectedNewLineLoc = new CodeLoc(2, 1);
        assertEquals(expectedNewLineLoc, reader.getCodeLoc());
    }

    @Test
    void expect() {
        String filePath = "src/test/java/com/auberer/compilerdesignlectureproject/reader/test.txt";
        Reader reader = new Reader(filePath);

        char expectedChar = 'T';
        assertDoesNotThrow(() -> reader.expect(expectedChar));

        char unexpectedChar = 'X';
        Exception exception = assertThrows(Exception.class, () -> reader.expect(unexpectedChar));
        assertTrue(exception.getMessage().contains("Expected character " + unexpectedChar));
    }

    @Test
    void isEOF() {
        String filePath = "src/test/java/com/auberer/compilerdesignlectureproject/reader/empty_file.txt";
        Reader reader = new Reader(filePath);

        assertTrue(reader.isEOF());
    }
}