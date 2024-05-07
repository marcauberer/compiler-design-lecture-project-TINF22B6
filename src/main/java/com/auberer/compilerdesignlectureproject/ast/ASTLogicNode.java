package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.Set;

public class ASTLogicNode extends ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitLogic(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return ASTStmtLstNode.getSelectionSet();
    }
}
