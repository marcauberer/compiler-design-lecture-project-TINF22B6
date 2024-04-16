package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.reader.Reader;

import org.junit.jupiter.api.Test;

public class LexerTest {
    @Test
    public void testStateMachineState() {
        Lexer l = new Lexer(new Reader("testfiles/xslt.txt"));
        l.advance();
    }
}
