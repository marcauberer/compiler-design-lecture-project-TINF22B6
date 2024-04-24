package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTDoWhileLoopNode extends ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDoWhile(this);
    }

    public static Set<TokenType> getSelectionSet() {
        Set<TokenType> firstSet = Set.of(TokenType.TOK_DO);
        return firstSet;
    }
}
