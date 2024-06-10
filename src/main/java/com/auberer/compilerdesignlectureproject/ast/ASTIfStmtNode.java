package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class ASTIfStmtNode extends ASTNode {
    public static Set<TokenType> getSelectionSet() {
        return Set.of(TokenType.TOK_IF);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitIf(this);
    }

    public ASTLogicalExprNode getCondition() {
        return getChild(ASTLogicalExprNode.class, 0);
    }

    public ASTStmtLstNode getBody() {
        return getChild(ASTStmtLstNode.class, 0);
    }

    private boolean hasAfterIf() {
        return !getChildren()
                .stream()
                .filter(astNode -> astNode instanceof ASTIfStmtNode)
                .collect(Collectors.toCollection(ArrayList::new)).isEmpty();
    }

    public ASTAfterIfNode getAfterIf() {
        return hasAfterIf() ? getChild(ASTAfterIfNode.class, 0) : null;
    }
}
