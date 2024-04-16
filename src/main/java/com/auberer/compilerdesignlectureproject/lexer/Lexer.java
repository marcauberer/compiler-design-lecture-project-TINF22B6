package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.Range;
import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import com.auberer.compilerdesignlectureproject.reader.IReader;
import java.util.Arrays;
import java.util.Set;

public class Lexer implements ILexer {

    private static final String[] keywordList = {"abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char",	"final", "interface", "static", "void", "class", "finally", "long", "strictfp",	"volatile", "const", "float", "native", "super", "while"};

    private final IReader reader;
    private String tokenValue;

    public Lexer(IReader reader) {
        this.reader = reader;
        reader.advance();
    }

    @Override
    public Token getToken() {
        if (tokenValue.isEmpty() && reader.isEOF()) {
            return new Token(TokenType.TOK_EOF, "\u001a", reader.getCodeLoc());
        }

        StateMachine stateMachine;
        char startChar = tokenValue.charAt(0);

        if (startChar == '"') {
            stateMachine = new StringLiteralStateMachine();
        } else if (startChar == '_' || startChar == '$') {
            stateMachine = new IdentifierStateMachine();
        } else if (new Range('0', '9').contains(startChar)) {
            if (tokenValue.contains(".")) {
                stateMachine = new DoubleLiteralStateMachine();
            }
            stateMachine = new IntegerLiteralStateMachine();
        } else if (new Range('A', 'Z').contains(startChar) || new Range('a', 'z').contains(startChar)) {
            if (Arrays.asList(keywordList).contains(tokenValue)) {
                stateMachine = new KeywordStateMachine(tokenValue);
            }
            stateMachine = new IdentifierStateMachine();
        } else {
            return new Token(TokenType.TOK_INVALID, tokenValue, reader.getCodeLoc());
        }

        stateMachine.init();
        stateMachine.reset();

        try {
            StateMachine finalStateMachine = stateMachine;
            tokenValue.chars().forEach(input -> finalStateMachine.processInput((char)input));
            return new Token(stateMachine.getTokenType(), tokenValue, reader.getCodeLoc());
        } catch (IllegalStateException e) {
            return new Token(TokenType.TOK_INVALID, tokenValue, reader.getCodeLoc());
        }
    }

    @Override
    public void advance() {
        StringBuilder tokenBuilder = new StringBuilder();

        while (!reader.isEOF() && (reader.getChar() == ' ' || reader.getChar() == '\n' || reader.getChar() == '\t')) {
            reader.advance();
        }

        while (!reader.isEOF() && reader.getChar() != ' ' && reader.getChar() != '\n' && reader.getChar() != '\t') {
            tokenBuilder.append(reader.getChar());
            if (reader.getChar() == '"') {
                do {
                    reader.advance();
                    tokenBuilder.append(reader.getChar());
                } while (reader.getChar() != '"');
            }
            reader.advance();

        }

        tokenValue = tokenBuilder.toString();
    }

    @Override
    public void expect(TokenType expectedType) throws Exception {
        TokenType realType = getToken().getType();
        if (expectedType != realType) {
            throw new Exception("Unexpected token (%s) at position %d:%d. Expected token: %s real token: %s"
                .formatted(tokenValue, reader.getCodeLoc().getLine(), reader.getCodeLoc().getColumn(), expectedType, realType));
        }
    }

    @Override
    public void expectOneOf(Set<TokenType> expectedTypes) throws Exception {
        TokenType realType = getToken().getType();
        if (!expectedTypes.contains(realType)) {
            throw new Exception("Unexpected token (%s) at position %d:%d. Expected token: %s real token: %s"
                .formatted(tokenValue, reader.getCodeLoc().getLine(), reader.getCodeLoc().getColumn(), expectedTypes.toString(), realType));
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
}