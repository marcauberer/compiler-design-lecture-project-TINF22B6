package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import java.util.Set;

public class ASTForNode extends ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitForLoop(this);
    }

    public static Set<TokenType> getSelectionSet() {
        return Set.of(TokenType.TOK_FOR);
    }


    public ASTStmtLstNode getBody() {
        return getChild(ASTStmtLstNode.class, 0);
    }

    public ASTAssignExprNode getInitialization() {
        return getChild(ASTAssignExprNode.class, 0);
    }

    public ASTAssignExprNode getCondition() {
        return getChild(ASTAssignExprNode.class, 1);
    }

    public ASTAssignExprNode getIncrement() {
        return getChild(ASTAssignExprNode.class, 2);
    }
}