package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class ASTPrefixExprNode extends ASTNode{
    @Setter
    public static PrefixOperator operator;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitPrefixExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
        Set<TokenType> followSet = Set.of(TokenType.TOK_PLUS, TokenType.TOK_MINUS);
        Set<TokenType> selectionSet = new HashSet<>();
        selectionSet.addAll(ASTAtomicExprNode.getSelectionSet());
        selectionSet.addAll(followSet);
        return selectionSet;
    }

    public enum PrefixOperator {
        PLUS,
        MINUS
    }
}
