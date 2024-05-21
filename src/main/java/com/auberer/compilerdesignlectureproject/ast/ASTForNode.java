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

    public ASTAssignStmtNode getInitialization() {
        return getChild(ASTAssignStmtNode.class, 0);
    }

    public ASTLogicalExprNode getCondition() {
        return getChild(ASTLogicalExprNode.class, 0);
    }

    // TODO: maybe change this to ASTExprNode after next lecture
    public ASTLogicalExprNode getIncrement() {
        return getChild(ASTLogicalExprNode.class, 1);
    }
}