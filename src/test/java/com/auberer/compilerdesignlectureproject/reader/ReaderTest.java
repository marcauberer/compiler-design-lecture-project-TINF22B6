package com.auberer.compilerdesignlectureproject.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReaderTest {
    /**
     * Dummy Test example
     */
    @Test
    @DisplayName("This is a Test Test")
    public void test() {
        System.out.printf("This is a test:%n");
        Assertions.assertEquals("Test", "Test");
    }

    /**
     * Content of file test.txt:
     *
     * Aliquam erat volutpat. Nunc eleifend leo vitae magna. In id erat non orci
     * commodo lobortis. Proin neque massa, cursus ut, gravida ut, lobortis eget,
     * lacus. Sed diam. Praesent fermentum tempor tellus. Nullam tempus. Mauris ac
     * felis vel velit tristique imperdiet. Donec at pede. Etiam vel neque nec dui
     * dignissim bibendum. Vivamus id enim. Phasellus neque orci, porta a, aliquet
     * quis, semper a, massa. Phasellus purus. Pellentesque tristique imperdiet
     * tortor. Nam euismod tellus id erat.
     */


    @Test
    @DisplayName("Test for getChar()")
    public void testGetChar_FirstChar() {
        Reader reader = new Reader(new ByteArrayInputStream("Alles".getBytes()));
        assertEquals(reader.getChar(), 'A');
        // Test for CodeLoc
        assertEquals(1, reader.getCodeLoc().line);
        assertEquals(1, reader.getCodeLoc().column);
    }

    @Test
    @DisplayName("Test if EOF works")
    public void testIsEOF() {
        Reader reader = new Reader(new ByteArrayInputStream("".getBytes()));
        reader.advance();
        assertEquals(reader.isEOF(), true);
    }
}
