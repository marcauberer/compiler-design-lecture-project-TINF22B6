package com.auberer.compilerdesignlectureproject.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

import com.auberer.compilerdesignlectureproject.reader.Reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LexerTest {
    @Test
    public void testStateMachineState() {
        Lexer l = new Lexer(new Reader("testfiles/xslt.txt"));
        l.advance();
    }

    @Test
    @DisplayName("Test if Integer works")
    public void test_IntegerLiteralStateMachine() {
        Lexer l = new Lexer(new Reader(new ByteArrayInputStream("500".getBytes())));
        l.advance();
        assertEquals(l.getToken().getText(), "500");
    }
}
