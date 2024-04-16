package com.auberer.compilerdesignlectureproject.lexer;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.reader.IReader;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

public class LexerTest {
    private Lexer lexer;
    private IReader reader;
    public void setUp() {
        String file = "testLexer.txt";
        reader = new Reader(file);
        lexer = new Lexer(reader);
    }
    @Test
    public void testPublicKeyword() {
        setUp();
        lexer.advance();

        if (!lexer.getToken().getType().equals(TokenType.KEYWORD)) {
            throw new AssertionError("Expected keyword TokenType.KEYWORD, but found " + lexer.getToken().getType());
        }
        if (!lexer.getToken().getText().equals("public")) {
            throw new AssertionError("Expected token text 'public', but found '" + lexer.getToken().getText() + "'");
        }
    }
    @Test
    public void testIntegerLiteral() {
        setUp();
        lexer.advance();

        if (!lexer.getToken().getType().equals(TokenType.TYPE_INT)) {
            throw new AssertionError("Expected TokenType.INTEGER_LITERAL, but found " + lexer.getToken().getType());
        }
        if (!lexer.getToken().getText().equals("123")) {
            throw new AssertionError("Expected token text '123', but found '" + lexer.getToken().getText() + "'");
        }
    }
    @Test
    public void testStringLiteral() {
        setUp();
        lexer.advance();

        if (!lexer.getToken().getType().equals(TokenType.TYPE_STRING)) {
            throw new AssertionError("Expected TokenType.STRING_LITERAL, but found " + lexer.getToken().getType());
        }
        if (!lexer.getToken().getText().equals("Hello, World!")) {
            throw new AssertionError("Expected token text 'Hello, World!', but found '" + lexer.getToken().getText() + "'");
        }
    }
    @Test
    public void testExpect() {
        setUp();
        lexer.advance();

        try {
            lexer.expect(TokenType.TYPE_INT);
            throw new AssertionError("Expected RuntimeException, but no exception was thrown");
        } catch (RuntimeException e) {

        }
    }
    @Test
    public void testExpectOneOf() {
        setUp();
        lexer.advance();

        Set<TokenType> expectedTypes = new HashSet<>();
        expectedTypes.add(TokenType.TYPE_STRING);
        expectedTypes.add(TokenType.IDENTIFIER);
        try {
            lexer.expectOneOf(expectedTypes);
            throw new AssertionError("Expected RuntimeException, but no exception was thrown");
        } catch (RuntimeException e) {
            Exception exception;
        }
    }
    @Test
    public void testIsEOF() {
        setUp();
        if (!lexer.isEOF()) {
            throw new AssertionError("Expected EOF, but EOF was not reached");
        }
    }

}
