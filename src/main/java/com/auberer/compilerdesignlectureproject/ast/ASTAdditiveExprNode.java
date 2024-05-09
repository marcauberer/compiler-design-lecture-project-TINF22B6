package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTAdditiveExprNode extends ASTNode{
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAdditiveExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
      return ASTCompareExprNode.getSelectionSet();
    }
}
