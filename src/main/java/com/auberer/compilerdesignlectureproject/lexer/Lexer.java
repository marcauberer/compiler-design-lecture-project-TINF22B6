package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.*;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import com.auberer.compilerdesignlectureproject.reader.IReader;

import java.util.Arrays;
import java.util.Set;

public class Lexer implements ILexer {

    private static final String[] keywordList = {
            "identifier", "integer_literal", "floating_point_literal", "string_literal", "char_literal",
            "boolean_literal", "null_literal",
            "+", "-", "*", "/", "%", "++", "--", "==", "!=", ">", "<", ">=", "<=", "&&", "||", "!",
            "&", "|", "^", "<<", ">>", ">>>", "~", "=", "+=", "-=", "*=", "/=", "%=", "<<=", ">>=",
            ">>>=", "&=", "|=", "^=", ":", "?", ".", ",", ";", "(", ")", "{", "}", "[", "]",
            "->", "@", "::"
    };

    private final IReader reader;
    private String tokenText;

    public Lexer(IReader reader) {
        this.reader = reader;
        reader.advance();
    }

    @Override
    public Token getToken() {
        if (tokenText.isEmpty() && reader.isEOF()) {
            return new Token(TokenType.TOK_EOF, "\u001a", reader.getCodeLoc());
        }

        StateMachine stateMachine = getStateMachineFor(tokenText);

        if (stateMachine == null) {
            return new Token(TokenType.TOK_INVALID, tokenText, reader.getCodeLoc());
        }

        stateMachine.init();
        stateMachine.reset();

        try {
            tokenText.chars().forEach(input -> stateMachine.processInput((char)input));
            return new Token(stateMachine.getTokenType(), tokenText, reader.getCodeLoc());
        } catch (IllegalStateException e) {
            return new Token(TokenType.TOK_INVALID, tokenText, reader.getCodeLoc());
        }
    }

    @Override
    public void advance() {
        tokenText = "";

        // skip whitespaces
        while (!reader.isEOF() && (reader.getChar() == ' ' || reader.getChar() == '\n' || reader.getChar() == '\t')) {
            reader.advance();
        }

        // read token chars
        while (!reader.isEOF() && reader.getChar() != ' ' && reader.getChar() != '\n' && reader.getChar() != '\t') {
            tokenText += reader.getChar();

            if (reader.getChar() == '"') {
                advanceStringLiteral();
            }

            reader.advance();
        }
    }

    @Override
    public void expect(TokenType expectedType) throws Exception {
        TokenType actualType = getToken().getType();
        if (expectedType != actualType) {
            throw new Exception("Unexpected token (%s) at %d:%d. Expected: %s actual: %s"
                    .formatted(tokenText, reader.getCodeLoc().getLine(), reader.getCodeLoc().getColumn(), expectedType, actualType));
        }
    }

    @Override
    public void expectOneOf(Set<TokenType> expectedTypes) throws Exception {
        TokenType actualType = getToken().getType();
        if (!expectedTypes.contains(actualType)) {
            throw new Exception("Unexpected token (%s) at %d:%d. Expected: %s actual: %s"
                    .formatted(tokenText, reader.getCodeLoc().getLine(), reader.getCodeLoc().getColumn(), expectedTypes.toString(), actualType));
        }
    }

    @Override
    public boolean isEOF() {
        return reader.isEOF();
    }

    @Override
    public CodeLoc getCodeLoc() {
        return reader.getCodeLoc();
    }

    private static StateMachine getStateMachineFor(String tokenText) {
        char firstChar = tokenText.charAt(0);

        if (firstChar == '"') {
            return new StringLiteralStateMachine();
        }

        if (firstChar == '_') {
            return new IdentifierStateMachine();
        }

        if (new Range('0', '9').contains(firstChar)) {
            if (tokenText.contains(".")) {
                return new DoubleLiteralStateMachine();
            }
            return new IntegerLiteralStateMachine();
        }

        if (new Range('A', 'Z').contains(firstChar) || new Range('a', 'z').contains(firstChar)) {
            if (Arrays.asList(keywordList).contains(tokenText)) {
                return new KeywordStateMachine(tokenText);
            }
            return new IdentifierStateMachine();
        }

        return null;
    }

    private void advanceStringLiteral() {
        do {
            reader.advance();
            tokenText += reader.getChar();
        } while (reader.getChar() != '"');
    }
}

