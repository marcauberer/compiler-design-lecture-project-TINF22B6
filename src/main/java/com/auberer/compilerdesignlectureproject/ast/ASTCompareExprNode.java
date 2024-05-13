package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class ASTCompareExprNode extends ASTNode{
    @Setter
    public static CompareOperator operator;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCompareExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
        Set<TokenType> selectionSet = new HashSet<>();
        selectionSet.addAll(ASTPrefixExprNode.getSelectionSet());
        return selectionSet;
    }

    public enum CompareOperator {
        EQUAL,
        NOT_EQUAL
    }
}
