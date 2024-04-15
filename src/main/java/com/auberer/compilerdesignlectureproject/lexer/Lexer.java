package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import java.util.Set;

public class Lexer implements ILexer {

    private static final String[] keywordList = {"abstract",	"continue",	"for", "new", "switch", "assert", "default", "goto", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char",	"final", "interface", "static", "void", "class", "finally", "long", "strictfp",	"volatile", "const", "float", "native", "super", "while"};

    @Override
    public Token getToken() throws Exception {
        return null;
    }

    @Override
    public void advance() {

    }

    @Override
    public void expect(TokenType expectedType) throws Exception {

    }

    @Override
    public void expectOneOf(Set<TokenType> expectedTypes) throws Exception {

    }

    @Override
    public boolean isEOF() {
        return false;
    }

    @Override
    public CodeLoc getCodeLoc() {
        return null;
    }
}
