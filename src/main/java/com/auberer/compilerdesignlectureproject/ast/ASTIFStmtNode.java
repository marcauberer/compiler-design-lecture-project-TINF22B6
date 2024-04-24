package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.ILexer;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.reader.Reader;

import java.util.HashSet;
import java.util.Set;

public class ASTIFStmtNode extends ASTNode{
    private static ILexer lexer;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitIf(this);
    }

    public static Set<TokenType> getSelectionSet() {

        return Set.of(TokenType.TOK_IF);
    }
}
