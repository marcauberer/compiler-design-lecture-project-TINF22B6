package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.reader.IReader;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

public class Lexer implements ILexer {

    private final IReader reader;
    private char currentChar;
    private CodeLoc codeLoc;

    public Lexer(IReader reader) {
        this.reader = reader;
        advance();
    }

    @Override
    public Token getToken() throws Exception {
        skipWhitespace();

        if (Character.isLetter(currentChar)) {
            return identifier();
        } else if (Character.isDigit(currentChar)) {
            return number();
        } else if (currentChar == '\0') {
            return new Token(TokenType.TOK_EOF, "", codeLoc);
        } else {
            return null;
        }
    }

    private Token identifier() {
        StringBuilder value = new StringBuilder();
        CodeLoc start = codeLoc;

        while (Character.isLetterOrDigit(currentChar) || currentChar == '_') {
            value.append(currentChar);
            advance();
        }

        return new Token(TokenType.TOK_IDENTIFIER, value.toString(), start);
    }

    private Token number () throws Exception {
        StringBuilder value = new StringBuilder();
        CodeLoc start = codeLoc;
        boolean isDecimal = false;

        while (Character.isDigit(currentChar) || currentChar == '.') {
            if (currentChar == '.') {
                if (isDecimal) {
                    throw new Exception("Invalid number format");
                }
                isDecimal = true;
            }
            value.append(currentChar);
            advance();
        }

        if (isDecimal) {
            return new Token(TokenType.TOK_DOUBLE, value.toString(), start);
        } else {
            return new Token(TokenType.TOK_INTEGER, value.toString(), start);
        }
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    @Override
    public void advance() {
        currentChar = reader.getChar();
        codeLoc = reader.getCodeLoc();
        reader.advance();
    }

    @Override
    public void expect(TokenType expectedType) throws Exception {
        Token token = getToken();
        if (token.getType() != expectedType) {
            throw new Exception("Expected token type " + expectedType + " but got " + token.getType() + " at " + token.getCodeLoc());
        }
        advance();
    }

    @Override
    public void expectOneOf(Set<TokenType> expectedTypes) throws Exception {
        Token token = getToken();
        if (!expectedTypes.contains(token.getType())) {
            throw new Exception("Expected one of token types " + expectedTypes + " but got " + token.getType() + " at " + token.getCodeLoc());
        }
        advance();
    }


    @Override
    public boolean isEOF() {
        return reader.isEOF();
    }

    @Override
    public CodeLoc getCodeLoc() {
        return codeLoc;
    }
}
