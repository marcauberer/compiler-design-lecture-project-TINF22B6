package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import com.auberer.compilerdesignlectureproject.reader.Reader;

import java.util.Set;

public class Lexer implements ILexer {
    private final Reader reader;
    private Token currToken;

    public Lexer(String filepath) {
        this.reader = new Reader(filepath);
        this.currToken = new Token(TokenType.TOK_EOF, "EOF", reader.getCodeLoc());
        this.advance();
    }

    @Override
    public Token getToken() {
        return this.currToken;
    }

    @Override
    public void advance() {
        while ((this.isWhitespace(this.reader.getChar())) && (!this.reader.isEOF())) {
            this.advance();
        }

        this.currToken = consumeToken();
    }

    @Override
    public void expect(TokenType expectedType) {
        if (this.currToken.getType() != expectedType) {
            throw new RuntimeException("Expected " + expectedType + " but got " + this.currToken.getType());
        }
        this.advance();
    }

    @Override
    public void expectOneOf(Set<TokenType> expectedTypes) {
        for (TokenType expectedType : expectedTypes) {
            if (this.currToken.getType() != expectedType) {
                throw new RuntimeException("Expected " + expectedType + " but got " + this.currToken.getType());
            }
        }
    }

    @Override
    public boolean isEOF() {
        return this.currToken.type == TokenType.TOK_EOF;
    }

    @Override
    public CodeLoc getCodeLoc() {
        return this.currToken.codeLoc;
    }

    private Token consumeToken() {
        char currChar = this.reader.getChar();
        CodeLoc codeLoc = this.reader.getCodeLoc();

        if (this.reader.isEOF()) {
            return new Token(TokenType.TOK_EOF, "EOF", codeLoc);
        }

        if ((isLetter(currChar)) || (currChar == '_')) {
            if (isUppercaseLetter(currChar)) {
                return this.consumeTypeIdentifier(codeLoc);
            } else {
                return this.consumeKeywordOrIdentifier(codeLoc);
            }
        }

        // Other schmexing stuff I like turtles and other stuff
        throw new RuntimeException("Got an unexpected character");
    }

    private Token consumeTypeIdentifier(CodeLoc codeLoc) {
        return null;
    }

    private Token consumeKeywordOrIdentifier(CodeLoc codeLoc) {
        return null;
    }

    private boolean isWhitespace(char c) {
        return (c == ' ') || (c == '\t') || (c == '\r') || (c == '\n') || (c == '\f');
    }

    private boolean isLetter(char c) {
        return (isLowercaseLetter(c)) || (isUppercaseLetter(c));
    }

    private boolean isUppercaseLetter(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    private boolean isLowercaseLetter(char c) {
        return (c >= 'a') && (c <= 'z');
    }

    private boolean isDigit(char c) {
        return (c >= '0') && (c <= '9');
    }
}