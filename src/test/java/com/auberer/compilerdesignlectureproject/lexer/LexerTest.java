package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LexerTest {
    private Lexer lexer;

    @BeforeEach
    void setUp() {
        String file = "src/test/java/com/auberer/compilerdesignlectureproject/lexer/testLexer.txt";
        Reader reader = new Reader(file);
        lexer = new Lexer(reader);
    }


    @Test
    void testStringLiteral() {
        lexer.expect(TokenType.TYPE_STRING);

        assertEquals("\"da\"", lexer.getToken().getText());
    }

    @Test
    void testIntegerOrDoubleLiteral() {
        Set<TokenType> possibleTokentypes = Set.of(TokenType.TYPE_DOUBLE,TokenType.TYPE_INT);
        lexer.advance();
        lexer.expectOneOf(possibleTokentypes);

        assertEquals("1231", lexer.getToken().getText());
    }


    @Test
    void testDoubleLiteral() {
        lexer.advance();
        lexer.advance();
        lexer.expect(TokenType.TYPE_DOUBLE);

        assertEquals("12.3", lexer.getToken().getText());
    }

    @Test
    void testIdentifierOrKeyword(){
        Set<TokenType> possibleTokentypes = Set.of(TokenType.IDENTIFIER,TokenType.KEYWORD);
        lexer.advance();
        lexer.advance();
        lexer.advance();
        lexer.expectOneOf(possibleTokentypes);
        assertEquals("public", lexer.getToken().getText());
    }

    @Test
    void testIdentifier(){
        lexer.advance();
        lexer.advance();
        lexer.advance();
        lexer.advance();
        lexer.expect(TokenType.IDENTIFIER);

        assertEquals("variable1", lexer.getToken().getText());
    }
}
