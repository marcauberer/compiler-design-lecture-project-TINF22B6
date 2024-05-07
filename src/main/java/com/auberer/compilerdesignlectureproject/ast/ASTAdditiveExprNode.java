package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class ASTAdditiveExprNode extends ASTNode{
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAdditiveExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
        Set<TokenType> selectionSet = new HashSet<>();
        selectionSet.addAll(ASTPrefixExprNode.getSelectionSet());
        return selectionSet;
    }
}
